# -*- coding: utf-8 -*-

import io
import os
import pprint
import logging as log

from queue import Queue

pp = pprint.PrettyPrinter(indent=2)


class FileReader(object):
    def __init__(self):
        pass

    def readline(self, filename, batch_size, included_header: bool, callback, args=None):
        assert callback

        with open(filename, 'rb') as fs:
            if included_header:
                header = fs.readline()

            bio = io.BytesIO()
            line_cnt = 0
            while True:
                line = fs.readline()
                if not line:
                    break
                bio.write(line)
                line_cnt += 1
                if line_cnt % batch_size == 0:
                    callback(bio.getvalue(), header, filename, *args)
                    bio = io.BytesIO()

            if bio.getbuffer().nbytes > 0:
                callback(bio.getvalue(), header, filename, *args)

            return line_cnt, fs.tell()

    def readlines(self, filename, batch_size, mode, included_header: bool, callback):
        assert callback
        with open(filename, mode) as fs:
            if included_header:
                header = fs.readline()

            bio = io.BytesIO()
            line_cnt = 0
            for line in fs.readlines():
                bio.write(line)
                line_cnt += 1

                if line_cnt % batch_size == 0:
                    callback(bio.getvalue(), filename)
                    bio = io.BytesIO()

            if bio.getbuffer().nbytes > 0:
                callback(bio.getvalue(), filename)

            return line_cnt, fs.tell()


def file_copy(src_filename: str, dst_filename: str, line_count: int):
    with open(src_filename, "rb") as fs:
        lines = fs.readlines()

    with open(dst_filename, "wb") as fs:
        if line_count > 0:
            cnt = 0
            for line in lines:
                fs.write(line)
                cnt += 1
                if cnt >= line_count:
                    return


if __name__ == '__main__':
    src_filename = "G:\\DWDB_RAW\\ODS\\PERIOD\\201901\\test\\OPT_BIS_MSG_BIT_BMS_INFO.31D.E7.001.csv"
    src_dir = os.path.dirname(src_filename)
    src_basename = os.path.basename(src_filename).split('.')[0]

    file_copy(src_filename, os.path.join(src_dir, src_basename + '.test.csv'), 3)
