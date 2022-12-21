# -*- coding: utf-8 -*-

import confluent_kafka
import time
import logging as log

from colorama import Fore

from confluent_kafka.cimpl import Message
from confluent_kafka.cimpl import KafkaException


class Producer(object):
    def __init__(self,
                 conf: dict,
                 produce_fail_retry_count,
                 produce_fail_retry_wait_time,
                 callback=None):
        self.produce_fail_retry_count = produce_fail_retry_count
        self.produce_fail_retry_wait_time = produce_fail_retry_wait_time

        self.pd = confluent_kafka.Producer(conf)
        self.callback = callback

    def produces(self, topic, values: list, key=None):
        for value in values:
            retry_count = 0
            while True:
                try:
                    self.pd.produce(topic,
                                    value,
                                    key=key,
                                    callback=self.delivery_callback)
                    break
                except BufferError as e:
                    log.warning(Fore.LIGHTRED_EX + 'Producer queue is full: {} messages awaiting delivery - try again'
                                .format(len(self.pd)) + Fore.RESET)

                    time.sleep(1)
                except KafkaException as e:
                    log.error(Fore.RED + 'KafkaException: - {}\n{}\n'.format(key, e) + Fore.RESET)

                    if retry_count >= self.produce_fail_retry_count:
                        log.warning(Fore.LIGHTRED_EX + 'key: {} - skip'.format(key) + Fore.RESET)
                        break

                    time.sleep(self.produce_fail_retry_wait_time)
                    retry_count += 1

                # Serve delivery callback queue.
                # NOTE: Since produce() is an asynchronous API this poll() call
                #       will most likely not serve the delivery callback for the
                #       last produce()d message.
                self.pd.poll(0)

        # Wait until all messages have been delivered
        # log.debug('Waiting for: {} deliveries'.format(len(self.pd)))
        self.pd.flush()

    def produce(self, topic, value, key=None, headers={}):
        retry_count = 0
        while True:
            try:
                self.pd.produce(topic,
                                value,
                                key=key,
                                headers=headers,
                                callback=self.delivery_callback)
                break
            except BufferError as e:
                log.warning(Fore.LIGHTRED_EX +
                            'Producer queue is full: {} messages awaiting delivery - try again'.format(len(self.pd)) +
                            Fore.RESET)

                time.sleep(1)
            except KafkaException as e:
                log.error(Fore.RED + 'KafkaException: - {}\n{}\n'.format(key, e) + Fore.RESET)
                retry_count += 1
                if self.produce_fail_retry_count < retry_count:
                    log.warning(Fore.LIGHTRED_EX + 'key: {} - skip'.format(key) + Fore.RESET)
                    break
                time.sleep(self.produce_fail_retry_wait_time)

        self.pd.flush()

    def poll(self):
        self.pd.poll(0)

    def flush(self):
        self.pd.flush()

    def awaiting_count(self):
        return len(self.pd)

    def delivery_callback(self, err, msg: Message):
        msg_dict = {'topic': msg.topic(),
                    'partition': msg.partition(),
                    'offset': msg.offset(),
                    'headers': msg.headers(),
                    'key': msg.key(),
                    'value': msg.value()}

        if self.callback:
            self.callback(err, msg_dict)


if __name__ == '__main__':

    import pprint

    from base.logger import set_std_logging

    pp = pprint.PrettyPrinter(indent=2)

    set_std_logging()

    conf = {"bootstrap.servers": "ec2-13-124-71-40.ap-northeast-2.compute.amazonaws.com:9092",
            "request.required.acks": 1,
            "socket.timeout.ms": 10000,
            "logger": log
            #"debug": "broker,producer"}
            }

    topic = "dwdb-ods"

    print(f"Kafka Configuration:\n{pp.pformat(conf)}\n{topic}\n")
    producer = Producer(conf, 2, 10000)
    value = b"hello"
    key = b"OCI_WTHR"
    producer.produce(topic, None, key, headers={"flag": "START_TABLE"})
    producer.produce(topic, None, key, headers={"flag": "START_FILE", "filename": "aaa.txt"})
    producer.produce(topic, None, key, headers={"flag": "END_TABLE"})
    producer.flush()
