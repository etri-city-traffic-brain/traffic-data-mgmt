# -*- coding: utf-8 -*-
import datetime
import os
import pprint
import logging as log
import sys
import time
import requests
import traceback

from colorama import Fore
from concurrent.futures import ThreadPoolExecutor

from base.config import INIConfig
from base.kafka.producer import Producer
from base.logger import set_logger_level
from base.path import get_file_list_excluded
from base.scheduler.scheduler import Scheduler
from base.sftp.sftp import SFTPClient, mkdir_p
from base.util import excel_to_dict
from service.mapping import MappingField, KafkaHeader
from service.produce.cli_server import CLIServer
from base.io.file_reader import FileReader

pp = pprint.PrettyPrinter(indent=2)

EXEC_NAME = os.path.splitext(os.path.basename(sys.argv[0]))[0]


class ProduceManager(object):
    def __init__(self, conf):
        self.conf: INIConfig = conf

        self.cliserv_executor: ThreadPoolExecutor = None
        self.read_executor: ThreadPoolExecutor = None
        self.sftp_executor: ThreadPoolExecutor = None

        # ----------------------------------------
        # App Configuration
        # ----------------------------------------

        section = 'app'
        mapping_excel_file = self.conf.get(section, 'mapping.excel.file')
        mapping_file = os.path.join(os.getenv('CONF_PATH'), mapping_excel_file)
        try:
            self.mapping_info = excel_to_dict(mapping_file, index_col=MappingField.KEY, na_values='', value_type='dict')
            log.debug(f"mapping_info size: {len(self.mapping_info)}")
        except Exception as e:
            log.error(Fore.RED + 'Exception: \n{}\n'.format(e))
            sys.exit(1)

        self.job_cron_schedule = self.conf.get(section, 'job.cron.schedule')
        self.job_identity = self.conf.get(section, 'job.identity')
        self.job_base_paths = self.conf.get(section, 'job.base.paths')

        self.file_pattern = self.conf.get(section, 'file.pattern')
        self.file_exclude_pattern = self.conf.get(section, 'file.exclude.pattern')

        self.scheduler = Scheduler()
        # ----------------------------------------
        # CLIServer Configuration
        # ----------------------------------------

        section = 'cli_server'
        server_port = self.conf.getint(section, 'server.port')

        self.cliserv = CLIServer(server_port, self.on_path)

        # ----------------------------------------
        # CLIServer Configuration
        # ----------------------------------------

        section = 'file_reader'
        self.included_header = self.conf.getboolean(section, 'included.header')
        self.read_batch_size = self.conf.getint(section, 'read.batch.size')

        # ----------------------------------------
        # Kafka Producer Configuration
        # ----------------------------------------

        section = 'kafka'

        produce_fail_retry_count = self.conf.getint(section, 'produce.fail.retry.count')
        produce_fail_retry_wait_time = self.conf.getint(section, 'produce.fail.retry.wait.time.ms') / 1000.0

        self.kafka_conf = {'logger': log}
        servers = self.conf.get(section, 'producer.bootstrap.servers')
        self.kafka_conf['bootstrap.servers'] = servers
        self.topic = self.conf.get(section, 'producer.topic')
        akcs = self.conf.getint(section, 'producer.request.required.acks')
        self.kafka_conf['request.required.acks'] = akcs
        batch_num = self.conf.getint(section, 'producer.batch.num.messages')

        self.kafka_conf['batch.num.messages'] = batch_num
        message_max_bytes = self.conf.getint(section, 'message.max.bytes')
        self.kafka_conf['message.max.bytes'] = message_max_bytes
        socket_timeout_ms = self.conf.getint(section, 'socket.timeout.ms')
        self.kafka_conf['socket.timeout.ms'] = socket_timeout_ms
        debug = self.conf.get(section, 'debug')
        if debug:
            self.kafka_conf['debug'] = debug

        log.debug('\nKafka Conf:\n{}\ntopic: {}\n'
                  .format(pprint.pformat(self.kafka_conf), self.topic))

        self.producer = Producer(self.kafka_conf,
                                 produce_fail_retry_count,
                                 produce_fail_retry_wait_time,
                                 self.on_delivery)

        # ----------------------------------------
        # SFTP Configuration
        # ----------------------------------------

        section = 'sftp'
        self.sftp_fail_retry_count = self.conf.getint(section, 'sftp.fail.retry.count')
        self.sftp_fail_retry_wait_time = self.conf.getint(section, 'sftp.fail.retry.wait.time.ms') /1000.0

        self.sftp_host = self.conf.get(section, 'sftp.host')
        self.sftp_port = self.conf.getint(section, 'sftp.port')
        self.sftp_username = self.conf.get(section, 'sftp.username')
        self.sftp_password = self.conf.get(section, 'sftp.password')
        if self.sftp_password == '':
            self.sftp_password = None
        sftp_private_key_file = self.conf.get(section, 'sftp.private.key.file')
        if sftp_private_key_file and sftp_private_key_file != '':
            self.sftp_key_file = os.path.join(os.getenv('DIR_PATH'), 'key', sftp_private_key_file)
        self.sftp_pkey = None

        self.sftp_remote_base_path = self.conf.get(section, 'sftp.remote.base.path')

        set_logger_level(Scheduler.LOGGER_NAME, log.INFO)
        set_logger_level(SFTPClient.LOGGER_NAME, log.INFO)

    def init(self):
        try:
            client = SFTPClient()
            self.sftp_pkey = client.read_key(self.sftp_key_file)
            client.connect(self.sftp_host, self.sftp_port, self.sftp_username, pkey=self.sftp_pkey)
            client.close()
        except Exception:
            trace = traceback.format_exc()
            log.error(Fore.RED + 'Exception: \n{}'.format(trace))
            sys.exit(1)

        try:
            self.scheduler.add_job(self.on_schedule,
                                   self.job_cron_schedule,
                                   args=(self.job_base_paths,),
                                   job_id=self.job_identity)
        except Exception:
            trace = traceback.format_exc()
            log.error(Fore.RED + 'Exception: \n{}'.format(trace))
            sys.exit(1)

        if self.cliserv_executor is None:
            self.cliserv_executor = ThreadPoolExecutor(max_workers=1, thread_name_prefix=self.cliserv.__class__.__name__)
        if self.read_executor is None:
            self.read_executor = ThreadPoolExecutor(max_workers=1, thread_name_prefix=self.__class__.__name__)
        if self.sftp_executor is None:
            self.sftp_executor = ThreadPoolExecutor(max_workers=1, thread_name_prefix=self.__class__.__name__)

    def start(self):
        assert self.cliserv_executor
        assert self.read_executor
        assert self.sftp_executor
        assert self.sftp_pkey

        self.cliserv_executor.submit(self.cliserv.start)
        self.scheduler.start()

    def stop(self):
        log.debug('{} stop'.format(self.__class__.__name__))
        assert self.cliserv_executor
        assert self.read_executor
        assert self.sftp_executor

        self.scheduler.shutdown()
        self.cliserv.stop()
        self.cliserv_executor.shutdown()
        self.read_executor.shutdown()
        self.sftp_executor.shutdown()

        await_cnt = self.producer.awaiting_count()
        log.debug('{} stop - end, await_count: {}'.format(self.__class__.__name__, await_cnt))

    def classify_extract_type(self, filenames: list):
        all_filenames = []
        period_filenames = []

        for filename in filenames:
            try:
                tablename = os.path.basename(filename).split('.')[0]
                info: dict = self.mapping_info.get(tablename)
                if info is None:
                    log.warning(Fore.LIGHTRED_EX + 'Unregistered filename: {} - skip'.format(filename) + Fore.RESET)
                    continue

                extract_type: str = info.get(MappingField.EXTRACT_TYPE)
                if extract_type.upper() == 'ALL' or extract_type.upper() == "":
                    all_filenames.append(filename)
                elif extract_type.upper() == 'PERIOD':
                    period_filenames.append(filename)
                else:
                    log.warning(Fore.LIGHTRED_EX + 'Unknown extract_type: {}, filename: {} - skip'
                                .format(extract_type, filename) + Fore.RESET)
                    continue
            except Exception:
                trace = traceback.format_exc()
                log.error(Fore.RED + 'Exception - filename: {}\n{}'.format(filename, trace) + Fore.RESET)
                continue

        return all_filenames, period_filenames

    def connect_sftp(self, client: SFTPClient):
        assert client

        retry_count = 0
        while True:
            try:
                log.info('SFTP - Connecting')
                client.connect(self.sftp_host, self.sftp_port, self.sftp_username, pkey=self.sftp_pkey)
                return True

            except Exception:
                trace = traceback.format_exc()
                log.warning(Fore.LIGHTRED_EX + f"Exception:\n{trace}" + Fore.RESET)

                client.close()
                if retry_count >= self.sftp_fail_retry_count:
                    return False

                time.sleep(self.sftp_fail_retry_wait_time)
                retry_count += 1
                log.debug("SFTP - Retry connecting")

    def put_sftp(self, client: SFTPClient, filename, remote_filename, callback=None):
        assert client

        try:
            if not client.valid() and not self.connect_sftp(client):
                log_msg = f"Failed sftp put: {filename} skip"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                return -1

            file_size = os.path.getsize(filename)
            client.mkdirs(os.path.dirname(remote_filename))

            transferred_bytes = client.put(filename, remote_filename, callback=callback)
            if file_size != transferred_bytes:
                log_msg = f"filename: {filename} - file_size: {file_size}, " \
                          f"transferred_bytes: {transferred_bytes} not equal"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
            return transferred_bytes

        except Exception:
            trace = traceback.format_exc()
            log_msg = f"Exception: {filename}\n{trace}"
            log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)

            client.close()
            return -1

    """ 
    ******************************************
    Processing
    ******************************************
    """

    def on_schedule(self, job_base_paths: str):
        """
        Scheduler에 의해 callback

        :param job_base_paths:
        :return:
        """

        log.debug("on_schedule() - START")

        now = datetime.datetime.now()
        base_paths: str = now.strftime(job_base_paths)
        work_base_paths: list = base_paths.split(',')
        for path in work_base_paths:
            self.on_path(path)

    def on_path(self, work_base_path: str):
        """
        CLIServer 의 on_command()에 의한 callback
        1. Scheduler thread, cliserv_executor thread

        :param work_base_path:
        :return:
        """
        log.debug(f"work_base_path: {work_base_path}")
        filenames = get_file_list_excluded(work_base_path, self.file_pattern, self.file_exclude_pattern)
        log.debug(f"path: {work_base_path} - file list\n{pp.pformat(filenames)}")

        if filenames is None or len(filenames) == 0:
            msg = f"WARNING: No file in base path - path: {work_base_path})"
            log.warning(Fore.LIGHTRED_EX + msg + Fore.RESET)
            return msg + '\n'

        all_filenames, period_filenames = self.classify_extract_type(filenames)
        alls_len = len(all_filenames)
        periods_len = len(period_filenames)

        if alls_len == 0 and periods_len == 0:
            msg = 'WARNING: No file to process - path: {}'.format(work_base_path)
            log.warning(Fore.LIGHTRED_EX + msg + Fore.RESET)
            return msg + '\n'

        if alls_len > 0:
            self.sftp_executor.submit(self.sftp_processing, work_base_path, all_filenames)

        if periods_len > 0:
            self.read_executor.submit(self.read_processing, work_base_path, period_filenames)

        return 'OK\n'

    def sftp_processing(self, work_base_path, all_filenames: list):
        """
        on_path()에서 executor submit
        2. sftp_executor thread

        :param localfile:
        :param remotefile:
        :return:
        """
        first_start_t = time.perf_counter()
        log.info(Fore.LIGHTYELLOW_EX +
                 f"SFTP START - file count: {len(all_filenames)}" +
                 Fore.RESET)

        now = datetime.datetime.now()
        remote_basepath: str = now.strftime(self.sftp_remote_base_path)
        client = SFTPClient()
        if not self.connect_sftp(client):
            log_msg = f"Failed sftp connect - Failed to send: \n" \
                     f"{pp.pformat(all_filenames)}"
            log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
            return

        try:
            for filename in all_filenames:
                start_t = time.perf_counter()
                basename = os.path.basename(filename)
                dirname = os.path.basename(os.path.dirname(filename))
                remote_filename = remote_basepath.rstrip('/') + '/' + dirname + '/' + basename

                log.info(f"SFTP START - {filename}")
                transferred_bytes = self.put_sftp(client, filename, remote_filename, callback=self.on_transferred)
                end_t = time.perf_counter()

                elapsed_time = end_t - start_t
                delta: str = str(datetime.timedelta(seconds=int(elapsed_time)))
                log_msg = f"SFTP END - {filename} file size: {format(transferred_bytes, ',')} byte, " \
                          f"elapsed time: {delta}({elapsed_time:.2f}) sec"
                log.info(Fore.LIGHTBLUE_EX + log_msg + Fore.RESET)

        except Exception:
            trace = traceback.format_exc()
            log.warning(Fore.LIGHTRED_EX + f"Exception:\n{trace}" + Fore.RESET)

        client.close()

        last_end_t = time.perf_counter()
        last_elapsed_time = last_end_t - first_start_t
        last_delta: str = str(datetime.timedelta(seconds=int(last_elapsed_time)))
        msg = f"END SFTP Processing {work_base_path} - time: {last_delta}({last_elapsed_time:.2f}) sec\n"
        log.info(Fore.LIGHTYELLOW_EX + msg + Fore.RESET)

    def read_processing(self, work_base_path: str, period_filenames: list):
        """
        on_path()의 executor submit
        3. read_executor thread

        :param period_filenames:
        :return:
        """
        first_start_t = time.perf_counter()
        table_count = 0
        log.info(Fore.LIGHTYELLOW_EX +
                 f"READ START - file count: {len(period_filenames)}" +
                 Fore.RESET)

        before_tablename = None
        tablename = None
        total_line_cnt = 0
        total_filesize = 0
        start_t = 0
        reader = FileReader()
        for filename in period_filenames:
            try:
                tablename = os.path.basename(filename).split('.')[0]
                if before_tablename != tablename:

                    # ------------------------------
                    # table 변경
                    # ------------------------------

                    if before_tablename:
                        # ------------------------------
                        # end table
                        # ------------------------------
                        end_t = time.perf_counter()
                        elapsed_time = end_t - start_t
                        delta = str(datetime.timedelta(seconds=int(elapsed_time)))
                        log_msg = f"READ END [{before_tablename}] - " \
                                  f"line count: {format(total_line_cnt, ',')} " \
                                  f"file size: {format(total_filesize, ',')} byte, " \
                                  f"elapsed time: {delta}({elapsed_time:.2f}) sec"
                        log.info(Fore.LIGHTBLUE_EX + log_msg + Fore.RESET)
                        headers = {KafkaHeader.FLAG: KafkaHeader.FLAG_END_TABLE}
                        self.producer.produce(self.topic, None, key=before_tablename, headers=headers)
                        table_count += 1

                    # ------------------------------
                    # start table
                    # ------------------------------
                    start_t = time.perf_counter()
                    before_tablename = tablename
                    total_line_cnt = 0
                    total_filesize = 0

                    log.info(Fore.LIGHTYELLOW_EX + f"READ START [{tablename}]" + Fore.RESET)
                    headers = {KafkaHeader.FLAG: KafkaHeader.FLAG_START_TABLE}
                    self.producer.produce(self.topic, None, key=tablename, headers=headers)

                # ------------------------------
                # start file
                # ------------------------------
                log.debug(f"READ START [{tablename}] - {filename}")
                headers = {KafkaHeader.FLAG: KafkaHeader.FLAG_START_FILE, KafkaHeader.FILENAME: filename}
                self.producer.produce(self.topic, None, key=tablename, headers=headers)
                line_cnt, filesize = reader.readlines(filename,
                                                      self.read_batch_size,
                                                      'rb',
                                                      self.included_header,
                                                      self.on_read)
                # ------------------------------
                # end file
                # ------------------------------

                total_line_cnt += line_cnt
                total_filesize += filesize

            except Exception:
                trace = traceback.format_exc()
                msg = Fore.RED + f"Exception - filename: {filename}\n{trace}" + Fore.RESET
                log.error(msg)
                continue

        # ------------------------------
        # end table
        # ------------------------------

        end_t = time.perf_counter()
        elapsed_time = end_t - start_t
        delta = str(datetime.timedelta(seconds=int(elapsed_time)))
        msg = f"READ END [{tablename}] - " \
              f"line count: {format(total_line_cnt, ',')} " \
              f"file size: {format(total_filesize, ',')} byte, " \
              f"elapsed time: {delta}({elapsed_time:.2f}) sec"
        log.info(Fore.LIGHTBLUE_EX + msg + Fore.RESET)

        headers = {KafkaHeader.FLAG: KafkaHeader.FLAG_END_TABLE}
        self.producer.produce(self.topic, None, key=tablename, headers=headers)
        table_count += 1

        last_end_t = time.perf_counter()
        elapsed_time = last_end_t - first_start_t
        delta = str(datetime.timedelta(seconds=int(elapsed_time)))
        msg = f"END Read Processing {work_base_path} - table_count: {table_count}, time: {delta}({elapsed_time:.2f}) sec\n"
        log.info(Fore.LIGHTYELLOW_EX + msg + Fore.RESET)

    def on_read(self, raw: bytes, filename):
        """
        FileReader 의 read()에 의한 callback
        3. read_executor thread

        :param key:
        :param bio:
        :return:
        """

        tablename = os.path.basename(filename).split('.')[0]
        key = f"{tablename}"
        headers = {KafkaHeader.FLAG: KafkaHeader.FLAG_DATA, KafkaHeader.FILENAME: filename}
        self.producer.produce(self.topic, raw, key=key, headers=headers)

    @staticmethod
    def on_delivery(err, msg: dict):
        if err:
            log.warning(Fore.LIGHTRED_EX + 'Failed delivery - topic: {}, key: {}, value: {}\n{}\n'
                        .format(msg['topic'], msg['key'], msg['value'], err) + Fore.RESET)
            return
        # log.debug(msg)

    @staticmethod
    def on_transferred(bytes, total_bytes):
        res = bytes / int(total_bytes) * 100
        # sys.stdout.write('\rComplete precent: %.2f %%' % (res))
        # sys.stdout.flush()
