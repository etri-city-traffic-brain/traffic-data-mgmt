#!/usr/bin/env python
# -*- coding: utf-8 -*-
import io
import sys
import logging as log
import pprint
import os
import threading
import time
import traceback
import requests

from threading import Lock

import psycopg2
from colorama import Fore
from concurrent.futures import ThreadPoolExecutor

from base.config import INIConfig
from base.db.postgresql import PostgreSQL
from base.kafka.admin import Admin
from base.kafka.consumer import Consumer
from base.util import excel_to_dict
from service.mapping import MappingField, KafkaHeader

pp = pprint.PrettyPrinter(indent=2)

EXEC_NAME = os.path.splitext(os.path.basename(sys.argv[0]))[0]


class ConsumeManager(object):
    def __init__(self, conf):
        self.conf: INIConfig = conf

        self.local = threading.local()
        self.exec_lock = Lock()
        self.consumer_executor = None
        self.write_executors = {}
        self.write_postgres_s = {}

        # ----------------------------------------
        # App Configuration
        # ----------------------------------------

        section = 'app'
        mapping_excel_file = self.conf.get(section, 'mapping.excel.file')
        mapping_file = os.path.join(os.getenv('CONF_PATH'), mapping_excel_file)
        try:
            self.mapping_info: dict = excel_to_dict(mapping_file, index_col=MappingField.KEY, na_values='', value_type='dict')
            log.debug(f"mapping_info size: {len(self.mapping_info)}")
        except Exception as e:
            log.error(Fore.RED + 'Exception: \n{}\n'.format(e) + Fore.RESET)
            sys.exit(1)

        self.execute_db_write = self.conf.getboolean(section, 'execute.db.write')
        self.message_charset = self.conf.get(section, 'message.charset')
        self.row_field_sep = self.conf.get(section, 'row.field.sep')

        # ----------------------------------------
        # PostgreSQL Configuration
        # ----------------------------------------

        section = 'db_writer'

        self.postgres_retry_limit = self.conf.getint(section, 'postgres.retry.limit')
        self.postgres_fail_retry_wait_time = self.conf.getint(section, 'postgres.retry.limit') / 1000.0

        self.database = self.conf.get(section, 'postgres.database')
        self.user = self.conf.get(section, 'postgres.user')
        self.password = self.conf.get(section, 'postgres.password')
        self.host = self.conf.get(section, 'postgres.host')
        self.port = self.conf.getint(section, 'postgres.port')

        # ----------------------------------------
        # Kafka Consumer Configuration
        # ----------------------------------------

        section = 'kafka'

        consume_num_messages = self.conf.getint(section, 'consume.num.messages')
        consume_timeout = self.conf.getfloat(section, 'consume.timeout')
        consume_fail_retry_wait_time = self.conf.getint(section, 'consume.fail.retry.wait.time.ms') / 1000.0

        self.kafka_conf = {'logger': log}
        self.consumer_bootstrap_servers = self.conf.get(section, 'consumer.bootstrap.servers')
        self.kafka_conf['bootstrap.servers'] = self.consumer_bootstrap_servers
        self.topics = self.conf.get(section, 'consumer.topics').split(',')
        self.consumer_group_id = self.conf.get(section, 'consumer.group.id')
        self.kafka_conf['group.id'] = self.consumer_group_id
        self.consumer_enable_auto_commit = self.conf.getboolean(section, 'consumer.enable.auto.commit')

        self.kafka_conf['enable.auto.commit'] = self.consumer_enable_auto_commit
        self.consumer_auto_offset_reset = self.conf.get(section, 'consumer.auto.offset.reset')
        self.kafka_conf['auto.offset.reset'] = self.consumer_auto_offset_reset
        self.consumer_fetch_max_bytes = self.conf.getint(section, 'consumer.fetch.max.bytes')
        self.kafka_conf['fetch.max.bytes'] = self.consumer_fetch_max_bytes

        message_max_bytes = self.conf.getint(section, 'message.max.bytes')
        self.kafka_conf['message.max.bytes'] = message_max_bytes
        self.socket_timeout_ms = self.conf.getint(section, 'socket.timeout.ms')
        self.kafka_conf['socket.timeout.ms'] = self.socket_timeout_ms
        self.debug = self.conf.get(section, 'debug')
        if self.debug:
            self.kafka_conf['debug'] = self.debug

        log.debug('\nKafka Conf:\n{}\ntopic: {}\n'
                  .format(pp.pformat(self.kafka_conf), self.topics))

        try:
            admin = Admin({"bootstrap.servers": self.consumer_bootstrap_servers,
                           "socket.timeout.ms": self.socket_timeout_ms})
            self.topics_info: dict = admin.get_topics()
            log.debug(f"topic info: {self.topics_info}")
        except Exception:
            trace = traceback.format_exc()
            log_msg = f"Exception:\n{trace}"
            log.error(Fore.RED + log_msg + Fore.RESET)
            sys.exit(1)

        self.consumer = Consumer(self.kafka_conf,
                                 consume_num_messages,
                                 consume_timeout,
                                 consume_fail_retry_wait_time)

    def init(self):
        if self.consumer_executor is None:
            self.consumer_executor = ThreadPoolExecutor(max_workers=1,
                                                        thread_name_prefix=self.consumer.__class__.__name__)
        try:
            for topic in self.topics:
                partitions: list = self.topics_info.get(topic)
                if partitions is None or len(partitions) == 0:
                    log_msg = f"topic: {topic} - No information on topic"
                    log.error(Fore.RED_EX + log_msg + Fore.RESET)
                    sys.exit(1)

                for no in partitions:
                    executor = self.write_executors.get(no)
                    postgres = self.write_postgres_s.get(no)
                    if executor is None and postgres is None:
                        self.write_executors[no] = ThreadPoolExecutor(max_workers=1,
                                                                      thread_name_prefix=f"Writer_{no}")
                        postgres = PostgreSQL()
                        postgres.connect(self.database, self.user, self.password, self.host, self.port, autocommit=False)
                        ver = postgres.execute_fetchone('SELECT version()')
                        log.debug(f"[{no}] {ver}")
                        postgres.close()
                        self.write_postgres_s[no] = postgres

            assert len(self.write_executors) == len(self.write_postgres_s)
            log_msg = f"write_executors: {len(self.write_executors)}\n" \
                     f"{pp.pformat(self.write_executors)}"
            log.debug(log_msg)
            log_msg = f"write_postgres_s: {len(self.write_postgres_s)}\n" \
                     f"{pp.pformat(self.write_postgres_s)}"
            log.debug(log_msg)

        except Exception:
            trace = traceback.format_exc()
            log_msg = f"Exception:\n{trace}"
            log.error(Fore.RED + log_msg + Fore.RESET)
            sys.exit(1)

    def start(self):
        assert self.consumer_executor
        assert len(self.write_executors) > 0

        self.consumer_executor.submit(self.consumer.start, self.topics, self.on_receive)

    def stop(self):
        log.debug('{} stop'.format(self.__class__.__name__))
        self.consumer.stop()
        if self.consumer_executor:
            self.consumer_executor.shutdown()
        self.consumer.close()

        if len(self.write_executors) > 0:
            for executor in self.write_executors.values():
                executor.shutdown()
        if len(self.write_postgres_s) > 0:
            for postgres in self.write_postgres_s.values():
                postgres.close()
        log.debug('{} stop - end'.format(self.__class__.__name__))

    def connect_db(self, postgres: PostgreSQL):
        assert postgres

        retry_cnt = 0
        while True:
            try:
                log.debug(f"{self.database} - Connecting")
                postgres.connect(self.database, self.user, self.password, self.host, self.port, autocommit=False)
                return True

            except psycopg2.OperationalError:
                trace = traceback.format_exc()
                log.warning(Fore.LIGHTRED_EX + f"Exception:\n{trace}" + Fore.RESET)

                postgres.close()
                if retry_cnt >= self.postgres_retry_limit:
                    return False

                time.sleep(self.postgres_fail_retry_wait_time)
                retry_cnt += 1
                log.debug(f"{self.database} - Retry connecting")

            except Exception:
                trace = traceback.format_exc()
                log.warning(Fore.LIGHTRED_EX + f"Exception:\n{trace}" + Fore.RESET)

                postgres.close()
                if retry_cnt >= self.postgres_retry_limit:
                    return False

                time.sleep(self.postgres_fail_retry_wait_time)
                retry_cnt += 1
                log.debug(f"{self.database} - Retry connecting")

                return False

    @staticmethod
    def get_header_value(headers):
        flag = None
        filename = None
        if headers:
            head_key: str
            for head_key, head_value in headers:
                if head_key.upper() == KafkaHeader.FLAG:
                    flag = head_value.decode()
                elif head_key.upper() == KafkaHeader.FILENAME:
                    filename = head_value.decode()
        return flag, filename

    # ----------------------------------------
    # callback
    # ----------------------------------------

    def on_receive(self, error, msg: dict):
        """
        Consumer 의 consume()에 의한 callback
        1. consumer_executor thread

        :param error:
        :param msg:
        :return:
        """
        key = None
        try:
            topic = msg['topic']
            partition = msg['partition']
            offset = msg['offset']

            key = msg['key']    # tablename
            assert key
            key = key.decode(self.message_charset)
            value = msg['value']
            if value:
                value = value.decode(self.message_charset)

            headers = msg['headers']
            flag, filename = self.get_header_value(headers)
            if error:
                log_msg = f"receive error - topic: {topic}, key: {key}\n" \
                         f"value: {value}\n{error}"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                return

            info: dict = self.mapping_info.get(key)
            if info is None:
                log_msg = f"key: {key} - Unregistered table, skip"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                return

            extract_type: str = info.get(MappingField.EXTRACT_TYPE)
            if extract_type.upper() != 'PERIOD':
                log_msg = f"{key} extract_type: {extract_type} - must be PERIOD type, {headers} skip"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                return

            schema = info.get(MappingField.SCHEMA_NAME)
            if schema is None:
                log_msg = f"{key}: does not exist key, {headers} skip\n{value}"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                return

            table = f"{schema}.{key}"
            if self.execute_db_write:
                with self.exec_lock:
                    executor = self.write_executors.get(partition)
                    postgres = self.write_postgres_s.get(partition)

                    assert executor and postgres
                    future = executor.submit(self.write_processing,
                                             table,
                                             value,
                                             key,
                                             postgres,
                                             partition,
                                             flag,
                                             filename)
            else:
                log_msg = f"[topic: {topic}, partition: {partition}, offset: {offset}]" \
                         f" - key: {key}, headers: {headers}\n{value}"
                log.debug(log_msg)

        except Exception:
            trace = traceback.format_exc()
            log_msg = f"Exception - key: {key}\n{trace}"
            log.error(Fore.RED + log_msg + Fore.RESET)
            return

    def write_processing(self,
                         table: str,
                         value: str,
                         key: str,
                         postgres: PostgreSQL,
                         partition,
                         flag,
                         filename):
        """
        on_receive()에서 submit
        1. write_executor thread
        """

        self.flag_processing(table, key, partition, flag, filename)
        if value is None:
            return

        try:
            if self.local.before_filename != filename:
                self.local.before_filename = filename
                log_msg = f"{partition} START_{flag} [{key}] - {filename}"
                log.debug(log_msg)

        except AttributeError:
            self.local.before_filename = filename
            log_msg = f"{partition} START_{flag} [{key}] - {filename}"
            log.debug(log_msg)

        except Exception:
            trace = traceback.format_exc()
            log_msg = f"Exception: [{table}] - {filename}\n{trace}"
            log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)

        while True:
            try:
                if not postgres.valid() and self.connect_db(postgres) is False:
                    log_msg = f"Failed insert: {table}] - {filename} skip"
                    log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                    break

                postgres.copy_from(io.StringIO(value), table, sep=self.row_field_sep, null='')
                postgres.commint()
                break

            except psycopg2.OperationalError:
                trace = traceback.format_exc()
                postgres.rollback()

                log_msg = f"Exception: [{table}] - {filename}\n{trace}"
                log.warning(log_msg)

                if self.connect_db(postgres) is False:
                    log_msg = f"Failed insert: {table}] - {filename} skip"
                    log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                    break

                continue
            except Exception:
                """
                psycopg2.errors.UniqueViolation: duplicate key value violates unique constraint
                """

                trace = traceback.format_exc()
                postgres.rollback()

                log_msg = f"Exception: [{table}] - {filename} skip\n{trace}"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)

                # postgres.close()
                break

    def flag_processing(self, table, key, partition, flag, filename):
        try:
            if flag:
                if flag.upper() == KafkaHeader.FLAG_START_TABLE:
                    log_msg = f"{partition} {KafkaHeader.FLAG_START_TABLE} [{key}]"
                    log.info(Fore.LIGHTYELLOW_EX + log_msg + Fore.RESET)
                elif flag.upper() == KafkaHeader.FLAG_END_TABLE:
                    log_msg = f"{partition} {KafkaHeader.FLAG_END_TABLE} [{key}]"
                    log.info(Fore.LIGHTBLUE_EX + log_msg + Fore.RESET)
                elif flag.upper() == KafkaHeader.FLAG_START_FILE:
                    log_msg = f"{partition} {KafkaHeader.FLAG_START_FILE} [{key}] - {filename}"
                    log.debug(log_msg)

        except Exception:
            trace = traceback.format_exc()
            log_msg = f"Exception: [{table}] - {filename}\n{trace}"
            log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
