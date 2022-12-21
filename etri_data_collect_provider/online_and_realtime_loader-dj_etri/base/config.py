#!/usr/bin/env python
# -*- coding: utf-8 -*-

import logging as log
import configparser
import os
import sys

from colorama import Fore


def setAppEnv():
    DIR_PATH = os.path.dirname(os.path.realpath(sys.argv[0]))
    os.environ['DIR_PATH'] = DIR_PATH

    EXEC_NAME = os.path.splitext(os.path.basename(sys.argv[0]))[0]
    os.environ['EXEC_NAME'] = EXEC_NAME

    CONF_PATH = os.path.join(DIR_PATH, 'conf')
    os.environ['CONF_PATH'] = CONF_PATH

    return DIR_PATH, EXEC_NAME, CONF_PATH


# --------------------------------
# INI Configuration
# --------------------------------

def get_config_section(conf_path, section_name):
    conf_parser = configparser.ConfigParser()
    conf_parser.read(conf_path)

    conf = {}
    section = conf_parser[section_name]
    for option in section:
        value = section[option]
        conf[option] = value

    return conf


class INIConfig:
    def __init__(self):
        self.parser = configparser.ConfigParser()

    def load(self, filenames):
        return self.parser.read(filenames)

    def get(self, section, option):
        try:
            return self.parser.get(section, option)
        except configparser.NoOptionError as e:
            log.info(Fore.LIGHTRED_EX + '{}'.format(e) + Fore.RESET)
            return None

    def getint(self, section, option):
        try:
            return self.parser.getint(section, option)
        except configparser.NoOptionError as e:
            log.info(Fore.LIGHTRED_EX + '{}'.format(e) + Fore.RESET)
            return None

    def getboolean(self, section, option):
        try:
            return self.parser.getboolean(section, option)
        except configparser.NoOptionError as e:
            log.info(Fore.LIGHTRED_EX + '{}'.format(e) + Fore.RESET)
            return None

    def getfloat(self, section, option):
        try:
            return self.parser.getfloat(section, option)
        except configparser.NoOptionError as e:
            log.info(Fore.LIGHTRED_EX + '{}'.format(e) + Fore.RESET)
            return None
