#!/usr/bin/env python
# -*- coding: utf-8 -*-

import logging as log
import threading

from enum import Enum
from colorama import Fore
from twisted.internet import reactor

from base.twisted.tcp_server import TCPServerFactory, TCPServerProtocol


class CMD(Enum):
    READ = 'READ'


class Status(Enum):
    STOP = 0
    START = 1


class CLIServer(object):
    def __init__(self, port, callback=None):
        self.port = port
        self.callback = callback

        self._lock = threading.Lock()
        self._status: Status = Status.STOP

    def _set_status(self, status: Status):
        with self._lock:
            self._status = status

    def get_status(self):
        with self._lock:
            return self._status

    def start(self):
        factory = TCPServerFactory(self.on_command)
        reactor.listenTCP(self.port, factory)
        self._set_status(Status.START)
        log.debug('reactor run')
        reactor.run(installSignalHandlers=False)
        log.debug('reactor run - end')

    def stop(self):
        log.debug('{} reactor - stop'.format(self.__class__.__name__))
        reactor.stop()
        self._set_status(Status.STOP)
        log.debug('{} reactor stop - end'.format(self.__class__.__name__))

    def on_command(self, client: TCPServerProtocol, lines: list):
        log.debug('lines: {}'.format(lines))

        # -------------------------------------
        # command 확인
        # -------------------------------------

        paths = []
        for line in lines:
            line_split = line.split(' ')
            if len(line_split) < 2 or line_split[0].upper() != CMD.READ.value:
                log_msg = f"WARNING: CMD={line} - Unknown command"
                log.warning(Fore.LIGHTRED_EX + log_msg + Fore.RESET)
                client.write((log_msg + '\n   ex) read <base path>' + '\n').encode())
                return

            paths.append(line_split[1])

        for base_path_str in paths:
            if self.callback:
                resp: str = self.callback(base_path_str)
                client.write(resp.encode())

