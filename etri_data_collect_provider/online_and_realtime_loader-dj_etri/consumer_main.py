#!/usr/bin/env python
# -*- coding: utf-8 -*-

import logging as log
import os
import pprint
import time

from colorama import init, Fore

from base.config import INIConfig, setAppEnv
from base.graceful import GracefulExit
from base.logger import set_std_file_logging
from service.consume.consume_manager import ConsumeManager

init(autoreset=True)
pp = pprint.PrettyPrinter(indent=2)

SEP_STR = "======================================"

DIR_PATH, EXEC_NAME, CONF_PATH = setAppEnv()

# ---------------------------------------
# log setting
# ---------------------------------------

log_filename = f"{EXEC_NAME}.log"
log_file = os.path.join(DIR_PATH, 'logs', log_filename)
set_std_file_logging(filename=log_file)


def main():
    log.info(Fore.LIGHTYELLOW_EX + f"{SEP_STR}" + Fore.RESET)
    log.info(Fore.LIGHTYELLOW_EX + f"PID: {os.getpid()} MAIN - START" + Fore.RESET)
    log.info(Fore.LIGHTYELLOW_EX + f"{SEP_STR}" + Fore.RESET)

    # ----------------------------------------
    # Configuration
    # ----------------------------------------

    conf = INIConfig()
    conf_filename = os.path.join(CONF_PATH, "consumer.ini")
    read_ok = conf.load(conf_filename)
    if len(read_ok) == 0:
        log.error(Fore.RED +
                  f"Can't load config file - conf_filename: {conf_filename}" +
                  Fore.RESET)
        exit(1)

    manager = ConsumeManager(conf)
    manager.init()
    manager.start()

    grace_exit = GracefulExit()
    while not grace_exit.is_stop:
        time.sleep(1.0)

    manager.stop()
    log.info(Fore.LIGHTYELLOW_EX + f"PID: {os.getpid()} MAIN - END" + Fore.RESET)


if __name__ == "__main__":
    main()
