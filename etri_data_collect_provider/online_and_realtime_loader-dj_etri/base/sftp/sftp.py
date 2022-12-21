#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import logging as log
import paramiko


def mkdir_p(sftp, remote_directory):
    """Change to this directory, recursively making new folders if needed.
    Returns True if any folders were created."""
    if remote_directory == '/':
        # absolute path so change directory to root
        sftp.chdir('/')
        return
    if remote_directory == '':
        # top-level relative directory must exist
        return
    try:
        sftp.chdir(remote_directory) # sub-directory exists
    except IOError:
        dirname, basename = os.path.split(remote_directory.rstrip('/'))
        mkdir_p(sftp, dirname) # make parent directories
        sftp.mkdir(basename) # sub-directory missing, so created it
        sftp.chdir(basename)
        return True


class SFTPClient(object):
    LOGGER_NAME = 'paramiko'

    def __init__(self):
        self._transport: paramiko.Transport = None
        self._sftp: paramiko.SFTPClient = None

    def __del__(self):
        self.close()

    def read_key(self, filename, password=None):
        return paramiko.RSAKey.from_private_key_file(filename, password)

    def connect(self, host, port, username, password=None, pkey=None):
        if self.valid():
            self.close()

        self._transport = paramiko.Transport((host, port))
        self._transport.connect(username=username, password=password, pkey=pkey)
        self._sftp = paramiko.SFTPClient.from_transport(self._transport)

    def valid(self):
        return self._sftp is not None and self._transport is not None

    def close(self):
        if self._transport:
            if self._sftp:
                self._sftp.close()
            self._transport.close()
            log.debug("SFTPClient connection is closed")

        self._transport = None
        self._sftp = None

    def mkdirs(self, dir_name):
        assert self._transport
        assert self._sftp

        mkdir_p(self._sftp, dir_name)

    def put(self, localpath, remotepath, callback=None, confirm=True):
        assert self._transport
        assert self._sftp

        result: paramiko.sftp_attr.SFTPAttributes = self._sftp.put(localpath, remotepath, callback=callback, confirm=confirm)
        return result.st_size

    def get(self, remote_path, local_path, callback=None):
        assert self._transport
        assert self._sftp

        self._sftp.get(remote_path, local_path, callback=callback)
