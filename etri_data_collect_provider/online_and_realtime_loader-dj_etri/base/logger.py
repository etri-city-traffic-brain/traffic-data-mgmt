#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import logging
import logging.handlers
import os
import sys

'''
- format: 
  %(asctime)s %(process)d %(levelname)+5s %(processName)s %(threadName)s
  [%(filename)s:%(funcName)s:%(lineno)d] %(message)s
'''


def set_logger_level(name, level=logging.DEBUG):
    logging.getLogger(name).setLevel(logging.INFO)


def set_std_simple_logging(name=None, level=logging.DEBUG, std=sys.stdout):
    logger = logging.getLogger(name)
    logger.setLevel(level)

    formatter = logging.Formatter('%(asctime)s %(process)d %(levelname)-5s '
                                  '[%(filename)s:%(funcName)s:%(lineno)d] %(message)s', "%Y-%m-%d %H:%M:%S")

    handler = logging.StreamHandler(std)
    handler.setFormatter(formatter)
    logger.addHandler(handler)


def set_std_logging(name=None, level=logging.DEBUG, std=sys.stdout):
    logger = logging.getLogger(name)
    logger.setLevel(level)

    formatter = logging.Formatter('%(asctime)s %(process)d %(levelname)-5s %(processName)s %(threadName)s '
                                  '[%(filename)s:%(funcName)s:%(lineno)d] %(message)s', "%Y-%m-%d %H:%M:%S")

    handler = logging.StreamHandler(std)
    handler.setFormatter(formatter)
    logger.addHandler(handler)


def set_std_file_logging(name=None, filename='./logs/app.log', level=logging.DEBUG):
    print(f"### log filename: {filename}")

    dirname = os.path.dirname(filename)
    if not os.path.exists(dirname):
        os.makedirs(dirname)

    logger = logging.getLogger(name)
    logger.setLevel(level)

    console_format = logging.Formatter('%(asctime)s %(levelname)-5s %(processName)s %(threadName)s '
                                       '[%(filename)s:%(funcName)s:%(lineno)d] %(message)s', "%Y-%m-%d %H:%M:%S")

    file_format = logging.Formatter('%(asctime)s %(process)d %(levelname)-5s %(processName)s %(threadName)s '
                                    '[%(filename)s:%(funcName)s:%(lineno)d] %(message)s', "%Y-%m-%d %H:%M:%S")

    console_handler = logging.StreamHandler(sys.stdout)
    console_handler.setFormatter(console_format)

    timed_handler = logging.handlers.TimedRotatingFileHandler(filename=filename,
                                                              when='midnight',
                                                              backupCount=10)
    timed_handler.setFormatter(file_format)
    logger.addHandler(console_handler)
    logger.addHandler(timed_handler)


def set_file_logging_config(filename, level=logging.DEBUG):
    logging.debug(f"log filename: {filename}")

    dirname = os.path.dirname(filename)
    if not os.path.exists(dirname):
        os.makedirs(dirname)

    console = logging.StreamHandler(sys.stdout)
    format = '%(asctime)s %(process)d %(levelname)-5s %(processName)s %(threadName)s ' \
             '[%(filename)s:%(funcName)s:%(lineno)d] %(message)s'
    timed = logging.handlers.TimedRotatingFileHandler(filename=filename,
                                                      when='midnight',
                                                      backupCount=10)
    logging.basicConfig(
        level=level,
        format=format,
        handlers=[console, timed])

