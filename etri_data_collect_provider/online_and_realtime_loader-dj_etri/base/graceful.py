#!/usr/bin/env python
# -*- coding: utf-8 -*-

import signal
import time
import logging as log
from colorama import Fore


class GracefulExit:
    is_stop = False

    def __init__(self):
        signal.signal(signal.SIGINT, self.exit_gracefully)
        signal.signal(signal.SIGTERM, self.exit_gracefully)

    def exit_gracefully(self, signum, frame):
        if signal.SIGINT == signum:
            log.info('signal: SIGINT({})'.format(signum))
        elif signal.SIGTERM == signum:
            log.info('signal: SIGTERM({})'.format(signum))
        else:
            log.info(Fore.LIGHTRED_EX + 'signal: {}'.format(signum))
        self.is_stop = True


if __name__ == '__main__':

    grace_exit = GracefulExit()
    while not grace_exit.is_stop:
        time.sleep(1.0)
        print("doing something in a loop ...")

    print("End of the program. I was killed gracefully :)")
