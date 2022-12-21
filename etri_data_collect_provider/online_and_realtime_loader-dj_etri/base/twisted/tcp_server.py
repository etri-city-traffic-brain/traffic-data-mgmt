#!/usr/bin/env python
# -*- coding: utf-8 -*-

import io
import logging as log
import pprint
import sys

from colorama import init, Fore
from twisted.internet import reactor, protocol
from twisted.internet.error import ConnectionLost, ConnectionDone

from base.util import make_token

pp = pprint.PrettyPrinter(indent=2)


class TCPServerProtocol(protocol.Protocol):
    def __init__(self):
        self.num = 0
        self.id = None

        self.factory: TCPServerFactory = None

    def connectionMade(self):
        self.factory.clients_num += 1
        self.num = self.factory.clients_num
        token = make_token()
        self.id = token
        client_details = str(self.transport.getPeer())
        log.info('connect client: {}({}) - {}'.format(self.id, self.num, client_details))
        # self.transport.loseConnection()

        if self in list(self.factory.clients.values()):
            log.error(Fore.RED + 'client duplicate')
            sys.exit(1)

        self.factory.clients[self.id]: dict = self

        clients = list(self.factory.clients.values())
        log.debug('clients: \n  {}'.format(pp.pformat(self.factory.clients)))

    def connectionLost(self, reason):
        if not reason.check(ConnectionDone):
            log.info(Fore.LIGHTRED_EX + "client id: {} ({})\n{}\n"
                     .format(self.id, self.num, reason))

        try:
            self.factory.clients.pop(self.id)
        except KeyError:
            log.error(Fore.RED + 'KeyError: {}[{}]', self.id, self.num)
            sys.exit(1)

        log.debug('client id: {} ({}) - lost, remain count: {}'
                  .format(self.id, self.num, len(self.factory.clients)))

    def dataReceived(self, data):
        buf = io.BytesIO(data)
        lines = [line.decode().strip() for line in buf.readlines()]
        if self.factory.callback:
            self.factory.callback(self, lines)

    def write(self, data):
        self.transport.write(data)
        self.transport.loseConnection()

    def write_prompt(self):
        self.transport.write(b'> ')


class TCPServerFactory(protocol.ServerFactory):
    protocol = TCPServerProtocol

    def __init__(self, callback):
        self.clients = {}
        self.clients_num = 0

        self.callback = callback


if __name__ == '__main__':
    from base.logger import set_std_logging

    init(autoreset=True)
    set_std_logging()

    def on_command(factory: TCPServerFactory, lines):
        pp.pprint(lines)

    factory = TCPServerFactory(on_command)
    reactor.listenTCP(8000, factory)
    reactor.run(installSignalHandlers=False)
