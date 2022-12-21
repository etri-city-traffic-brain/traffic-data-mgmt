#!/usr/bin/env python
# -*- coding: utf-8 -*-

import logging as log

import confluent_kafka.admin
from colorama import Fore


class Admin:
    def __init__(self, conf: dict):
        self.adm = confluent_kafka.admin.AdminClient(conf)

    def get_topics(self):
        meta: confluent_kafka.admin.ClusterMetadata = self.adm.list_topics()

        result = {}
        info: confluent_kafka.admin.TopicMetadata
        topic: str
        for topic, info in meta.topics.items():
            if topic != "__consumer_offsets":
                if info.error:
                    log_msg = Fore.LIGHTRED_EX + f"error: {info.error} - " \
                                                f"topic: {topic}, partitions: {info.partitions}" + Fore.RESET
                    log.warning(log_msg)
                    continue
                log.debug(f"topic: {topic} - partitions: {info.partitions}")
                result[topic] = info.partitions.keys()

        return result

    def create_topics(self, topics: list, num_partitions=3, replication_factor=1):
        if len(topics) == 0:
            log.warning(Fore.LIGHTRED_EX + 'topic: [] empty' + Fore.RESET)
            return
        
        new_topics = []
        for topic in topics:
            tp = confluent_kafka.admin.NewTopic(topic,
                                                num_partitions=num_partitions,
                                                replication_factor=replication_factor)
            new_topics.append(tp)

        # Call create_topics to asynchronously create topics. A dict
        # of <topic,future> is returned.
        if len(new_topics) > 0:
            future = self.adm.create_topics(new_topics)

            # Wait for each operation to finish.
            for topic, f in future.items():
                try:
                    f.result()  # The result itself is None
                    log.info(Fore.LIGHTGREEN_EX + 'Topic: {} created'.format(topic) + Fore.RESET)
                except Exception as e:
                    log.warning(Fore.LIGHTRED_EX + 'Failed to create topic {}: {}'.format(topic, e) + Fore.RESET)

    def delete_topics(self, topics: list):
        future = self.adm.delete_topics(topics)

        for topic, f in future.items():
            try:
                f.result()  # The result itself is None
                log.info(Fore.LIGHTGREEN_EX + 'Topic: {} deleted'.format(topic) + Fore.RESET)
            except Exception as e:
                log.warning(Fore.LIGHTRED_EX + 'Failed to create topic {}: {}'.format(topic, e) + Fore.RESET)


if __name__ == '__main__':
    import json
    from base.logger import set_std_logging

    set_std_logging()

    def stats_cb(json_str: str):
        stats = json.loads(json_str)
        log.debug('KAFKA Stats: {}\n'.format(stats))


    BOOTSTRAP_SERVERS = 'ec2-13-124-71-40.ap-northeast-2.compute.amazonaws.com:9092'
    admin = Admin({'bootstrap.servers': BOOTSTRAP_SERVERS,
                   'socket.timeout.ms': 10000,
                   'statistics.interval.ms': 10000,
                   'stats_cb': stats_cb})
                   # 'debug': 'broker,admin'})

    admin.get_topics()
