#!/usr/bin/env python
# -*- coding: utf-8 -*-
import fnmatch
import os
import shutil


def move_file(src, dst):
    dirname = os.path.dirname(dst)
    if not os.path.exists(dirname):
        os.makedirs(dirname)

    shutil.move(src, dst)


class FileIO:
    def __init__(self):
        self.fd = None

    def __del__(self):
        if self.fd is not None:
            self.fd.close()
            self.fd = None

    def open(self, filename, mode):
        if self.fd is not None:
            self.fd.close()

        self.fd = open(filename, mode=mode)

    def open_read(self, filename, mode='rt', encoding='UTF-8'):
        if self.fd is not None:
            self.fd.close()

        self.fd = open(filename, mode=mode, encoding=encoding)

    def open_write(self, filename, mode='wt', encoding='UTF-8'):
        if self.fd is not None:
            self.fd.close()

        self.fd = open(filename, mode=mode, encoding=encoding)

    def close(self):
        if self.fd is not None:
            self.fd.close()
            self.fd = None

    def get_stat(self):
        assert self.fd is not None
        stat = os.fstat(self.fd)
        # return stat.st_atime, stat.st_ctime, stat.st_mtime, stat.st_size
        return stat

    def read(self, size=None):
        assert self.fd is not None

        if size is None:
            return self.fd.read()
        else:
            return self.fd.read(size)

    def readLine(self):
        assert self.fd is not None
        return self.fd.readline()

    def readLines(self):
        assert self.fd is not None
        return self.fd.readlines()

    # Set the file's current position, like stdio's fseek().
    def seek(self, offset):
        assert self.fd is not None
        self.fd.seek(offset)

    def truncate(self):
        assert self.fd is not None
        self.fd.truncate()

    def fileno(self):
        assert self.fd is not None
        return self.fd.fileno()

    # Flush the internal buffer, like stdio's fflush().
    def flush(self):
        assert self.fd is not None
        self.fd.flush()
