#!/usr/bin/env python
# -*- coding: utf-8 -*-

import time
import logging as log

from apscheduler.jobstores.base import JobLookupError
from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.triggers.cron import CronTrigger


class Scheduler:
    LOGGER_NAME = 'apscheduler.scheduler'

    def __init__(self):

        """
        self.sched = BlockingScheduler()
        self.sched = AsyncIOScheduler()
        """

        self.sched = BackgroundScheduler()

    def start(self):
        self.sched.start()

    def shutdown(self):
        self.sched.shutdown(wait=True)

    def add_job(self, func, cron_expr, args=None, job_id=None):
        cron = CronTrigger.from_crontab(cron_expr)
        self.sched.add_job(func=func, trigger=cron, args=args, id=job_id, misfire_grace_time=60)

    def remove_job(self, job_id):
        self.sched.remove_job(job_id)


if __name__ == '__main__':

    from base.logger import set_std_logging

    set_std_logging()
    log.getLogger("apscheduler.scheduler").setLevel(log.INFO)


    def hello(job_id, cron_expr):
        log.debug("Scheduler process_id[{}] : {}".format(job_id, time.localtime()))


    cron_expr = '0 21 * * *'
    scheduler = Scheduler()
    scheduler.add_job(hello, cron_expr, 'hello job')
    scheduler.start()
    while True:
        print('sleep')
        time.sleep(10)
        scheduler.shutdown()
        print('while end')
        break

    print('main end')
