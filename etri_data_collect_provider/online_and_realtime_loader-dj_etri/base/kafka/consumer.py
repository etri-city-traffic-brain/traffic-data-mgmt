#!/usr/bin/env python
# -*- coding: utf-8 -*-

import logging as log
import pprint
import time
import traceback
from threading import Event

import confluent_kafka

from colorama import Fore, init
from confluent_kafka.cimpl import KafkaError, Message


class Consumer(object):
    def __init__(self,
                 conf: dict,
                 consume_num_messages,
                 consume_timeout,
                 consume_fail_retry_wait_time):

        self.conf = conf
        self.consume_num_messages = consume_num_messages
        self.consume_timeout = consume_timeout
        self.consume_fail_retry_wait_time = consume_fail_retry_wait_time

        self.is_run = Event()
        self.cs = confluent_kafka.Consumer(conf)

    def close(self):
        self.cs.close()

    def start(self, topics: list, callback):
        log.debug('subscribe topics: {} - START'.format(topics))
        self.subscribe(topics)

        self.is_run.set()
        while self.is_run.is_set():
            try:
                self.consume(callback)
            except KeyboardInterrupt as e:
                log.info(Fore.LIGHTRED_EX + 'KeyboardInterrupt: Aborted by user\n{}\n'.format(e))
                self.is_run.clear()

            except KafkaError:
                trace = traceback.format_exc()
                log.error(Fore.RED + 'KafkaError: \n{}\n'.format(trace))
                self.close()

                time.sleep(self.consume_fail_retry_wait_time)
                log.info('subscribe topics: {} - RETRY'.format(topics))
                self.subscribe(topics)

        log.info('subscribe topics: {} - END'.format(topics))

    def stop(self):
        log.debug('{} consume stop'.format(self.__class__.__name__))
        self.is_run.clear()
        log.debug('{} consume stop - end'.format(self.__class__.__name__))

    def consume(self, callback=None):
        msgs: list
        msgs = self.cs.consume(num_messages=self.consume_num_messages, timeout=self.consume_timeout)
        if len(msgs) == 0:
            return

        #log.debug(f"consume message size: {len(msgs)}")
        msg: Message
        for msg in msgs:
            error = msg.error()
            topic = msg.topic()
            partition = msg.partition()
            offset = msg.offset()
            headers = msg.headers()
            key = msg.key()
            value = msg.value()

            msg_dict = {'topic': topic,
                        'partition': partition,
                        'offset': offset,
                        'headers': headers,
                        'key': key,
                        'value': value}

            if callback:
                callback(error, msg_dict)

    #  Called when the topic is assigned.
    def on_assign(self, consumer, partitions):
        part: confluent_kafka.TopicPartition
        for part in partitions:
            log.info(Fore.LIGHTYELLOW_EX +
                     'kafka assignment topic: {}, partition: {} offset: {} - START'
                     .format(part.topic, part.partition, part.offset) +
                     Fore.RESET)

    def subscribe(self, topics):
        self.cs.subscribe(topics, on_assign=self.on_assign)


if __name__ == '__main__':

    '''
    set PYTHONPATH=.
    $env:PYTHONPATH='.'

    export PYTHONPATH=.
    python base/kafka/consumer.py
    '''
    init(autoreset=True)
    pp = pprint.PrettyPrinter(indent=2)

    from base.logger import set_std_logging

    set_std_logging()

    conf = {'logger': log}

    conf['bootstrap.servers'] = 'ec2-13-124-71-40.ap-northeast-2.compute.amazonaws.com:9092'
    topics = 'dj-etri-dwdb-ods'.split(',')

    conf['group.id'] = 'online_loader'
    conf['enable.auto.commit'] = 'true'
    conf['auto.offset.reset'] = 'earliest'
    conf['socket.timeout.ms'] = 10000
    # conf['debug'] = 'broker'

    log.debug('\nKafka Conf:\n{}\ntopic: {}\n'
              .format(pp.pformat(conf), topics))
    consumer = Consumer(conf)

    log.debug('subscribe topics: {}'.format(topics))
    consumer.subscribe(topics)

    while True:
        try:
            msgs = consumer.consume(num_messages=10000, timeout=1.0)
            if len(msgs) < 1:
                continue

            for msg in msgs:
                if msg.error():
                    log.error("kafka exception: {}".format(msg.error()))
                else:
                    # read message
                    log.debug("[{} topic partition=({}), offset={}], key={}, value={}"
                              .format(msg.topic(), msg.partition(), msg.offset(), msg.key(), msg.value()))
        except KeyboardInterrupt:
            log.info('Aborted by user\n')
            break
        except Exception as e:
            log.warning(Fore.LIGHTRED_EX + '{}'.format(e))
            break

    log.info(Fore.LIGHTYELLOW_EX + "kafka subscribe - END")

