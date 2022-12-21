#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import logging as log
import traceback

import psycopg2
import psycopg2.errors

ISOLEVEL = psycopg2.extensions.ISOLATION_LEVEL_AUTOCOMMIT


class PostgreSQL(object):
    def __init__(self):
        self._conn = None
        self._cursor = None
        self._last_use_time = None

    def __del__(self):
        self.close()

    def connect(self, database: str, user: str, password: str, host: str, port: int, autocommit=False):
        self.close()
        self._conn = psycopg2.connect(database=database,
                                      user=user,
                                      password=password,
                                      host=host,
                                      port=port)

        self._conn.autocommit = autocommit
        self._cursor = self._conn.cursor()
        self._last_use_time = datetime.datetime.now()

    def valid(self):
        return self._cursor is not None and self._conn is not None

    def close(self):
        if self._conn:
            if self._cursor:
                self._cursor.close()
            self._conn.close()
            log.debug("PostgreSQL connection is closed")

        self._conn = None
        self._cursor = None
        self._last_use_time = None

    def is_master(self):
        row = self.execute_fetchone("select pg_is_in_recovery();")
        self._last_use_time = datetime.datetime.now()
        if row and row[0]:
            return False
        return True

    def last_use_time(self):
        return self._last_use_time

    def execute_fetchone(self, sql: str):
        assert self._cursor is not None
        self._cursor.execute(sql)
        """
        fetchone() -> tuple or None

        Return the next row of a query result set in the form of a tuple (by
        default) or using the sequence factory previously set in the
        `row_factory` attribute. Return `!None` when no more data is available.
        """
        result = self._cursor.fetchone()
        self._last_use_time = datetime.datetime.now()
        return result

    def execute_fetchall(self, sql: str):
        assert self._cursor is not None
        self._cursor.execute(sql)
        """
        fetchall() -> list of tuple

        Return all the remaining rows of a query result set.

        Rows are returned in the form of a list of tuples (by default) or using
        the sequence factory previously set in the `row_factory` attribute.
        Return `!None` when no more data is available.
        """
        result = self._cursor.fetchall()
        self._last_use_time = datetime.datetime.now()
        return result

    def execute_fetchmany(self, sql: str, batch_size, callback, args):
        assert self._cursor is not None
        """
        fetchmany(size=self.arraysize) -> list of tuple

        Return the next `size` rows of a query result set in the form of a list
        of tuples (by default) or using the sequence factory previously set in
        the `row_factory` attribute.

        Return an empty list when no more data is available.
        """
        cnt = 0
        self._cursor.execute(sql)
        while True:
            rows = self._cursor.fetchmany(batch_size)
            self._last_use_time = datetime.datetime.now()
            if not rows:
                break
            cnt += len(rows)
            callback(rows, *args)

        return cnt

    def copy_from(self, file, table, sep='\t', null='\\n', size=8192, columns=None):
        assert self._cursor
        """ copy_from(file, table, sep='\t', null='\\N', size=8192, columns=None) -- Copy table from file. """
        # copy t1(a, b, c) from 'test.csv' using delimiters ',' with NULL ''
        self._cursor.copy_from(file, table, sep=sep, null=null, size=size, columns=columns)
        self._last_use_time = datetime.datetime.now()

    def commint(self):
        assert self._conn
        self._conn.commit()
        self._last_use_time = datetime.datetime.now()

    def rollback(self):
        assert self._conn
        self._conn.rollback()
        self._last_use_time = datetime.datetime.now()


if __name__ == '__main__':

    import sys
    import pprint
    pp = pprint.PrettyPrinter(indent=2)

    database = 'dj_etri_db'
    user = 'dj_etri'
    password = 'dj_etri123.$'
    host = 'ec2-13-124-71-40.ap-northeast-2.compute.amazonaws.co'
    port = 5432

    postgres = PostgreSQL()
    try:
        postgres.connect(database, user, password, host, port)
        ver = postgres.execute_fetchone('SELECT version()')
        print(ver)
        results: list = postgres.execute_fetchall('SELECT * FROM ods.oci_atms_cnt')
        pp.pprint(results)

    except psycopg2.OperationalError:
        trace = traceback.format_exc()
        sys.stderr.write(trace)
    except psycopg2.errors.InvalidTextRepresentation:
        trace = traceback.format_exc()
        sys.stderr.write(trace)
    except Exception:
        trace = traceback.format_exc()
        sys.stderr.write(trace)
