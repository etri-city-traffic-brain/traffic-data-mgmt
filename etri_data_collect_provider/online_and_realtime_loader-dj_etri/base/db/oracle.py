#!/usr/bin/env python
# -*- coding: utf-8 -*-

import cx_Oracle


class Oracle(object):
    def __init__(self):
        self.conn: cx_Oracle.Connection = None
        self.cur: cx_Oracle.Cursor = None

    def __del__(self):
        self.close()

    def connect(self, host: str, port: int, database: str, user: str, password: str, autocommit=False):
        self.close()

        connect_url = f"{user}/{password}@{host}:{port}/{database}"
        self.conn = cx_Oracle.connect(connect_url)
        self.conn.autocommit = autocommit
        self.cur = self.conn.cursor()

    def valid(self):
        if self.conn and self.cur:
            return True
        else:
            return False

    def close(self):
        if self.cur:
            self.cur.close()
            self.cur = None

        if self.conn:
            self.conn.close()
            self.conn = None

    def execute_fetchone(self, sql: str):
        assert self.valid()

        self.cur: cx_Oracle.Cursor
        self.cur.execute(sql)
        return self.cur.fetchone()

    def execute_fetchall(self, sql: str):
        assert self.valid()

        self.cur.execute(sql)
        return self.cur.fetchall()

    def execute_fetchmany(self, sql: str, batch_size, callback, args):
        assert self.valid()

        cnt = 0
        self.cur: cx_Oracle.Cursor
        self.cur.execute(sql)
        while True:
            rows = self.cur.fetchmany(batch_size)
            if not rows:
                break
            cnt += len(rows)
            callback(rows, *args)

        return cnt

    def commint(self):
        assert self.conn is not None
        self.conn.commit()

    def rollback(self):
        assert self.conn is not None
        self.conn.rollback()


def simple_test():
    import pprint
    pp = pprint.PrettyPrinter(indent=2)

    USER = 'hr'
    PASS = 'hr'
    URL = 'localhost/orcl'

    conn = cx_Oracle.connect(USER, PASS, URL)
    print(type(conn))
    cursor = conn.cursor()
    print(type(cursor))

    conn.autocommit = False
    cursor.execute('SELECT * FROM v$VERSION')
    ver = cursor.fetchone()[0]
    print(ver)


if __name__ == '__main__':
    simple_test()

