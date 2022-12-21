#!/usr/bin/env python
# -*- coding: utf-8 -*-
import _csv
import csv
import io


class CSVWorker(object):
    def __init__(self):
        self.csv_worker = None
        self.fs = None

    def __del__(self):
        self.close()

    def open_write(self, filename, mode="wb", encoding=None, newline=None, delimiter=","):
        self.close()

        self.fs = open(filename, mode=mode, encoding=encoding, newline=newline)
        self.csv_worker: _csv.writer = csv.writer(self.fs, delimiter=delimiter)

    def open_read(self, filename, mode="rb", encoding=None, newline=None, delimiter=","):
        self.close()

        self.fs = open(filename, mode=mode, encoding=encoding, newline=newline)
        self.csv_worker: _csv.reader = csv.reader(self.fs, delimiter=delimiter)

    def valid(self):
        if self.fs and self.csv_worker:
            return True
        else:
            return False

    def close(self):
        if self.fs:
            self.fs.close()
            self.fs = None

            self.csv_worker = None

    def read(self, batch_size: int, included_header: bool, callback, args):
        assert self.valid()

        header = None
        if included_header:
            header = next(self.csv_worker)

        rows = []
        cnt = 0

        self.csv_worker: csv.reader
        for row in self.csv_worker:
            rows.append(row)
            cnt += 1

            if batch_size % cnt == 0:
                callback(rows, header, *args)
                rows = []

        if len(rows) > 0:
            callback(rows, header, *args)

        return cnt

    def writerow(self, row: list):
        assert self.valid()

        self.csv_worker: csv.writer
        self.csv_worker.writerow(row)

    def writerows(self, rows: list):
        assert self.valid()

        self.csv_worker: csv.writer
        self.csv_worker.writerow(rows)

    def flush(self):
        assert self.valid()

        self.fs.flush()


if __name__ == '__main__':
    pass
