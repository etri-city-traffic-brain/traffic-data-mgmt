#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json
import os
import secrets
import pandas as pd
import numpy as np


def makedirs(path):
    os.makedirs(path)


def csv_file_to_dict(filename, key_idx, value_idx, sep) -> dict:
    dt = {}
    with open(filename) as f:
        line: str
        for line in f:
            if line[0] == '#':
                continue
            column = line.strip().split(sep=sep)
            dt[column[key_idx]] = column[value_idx]

    return dt


def excel_to_dict(io, sheet_name=0, header=0, names=None, index_col=None, na_values='NaN', comment='#', value_type='list'):

    try:
        df = pd.read_excel(io=io,
                           sheet_name=sheet_name,
                           header=header,
                           names=names,
                           index_col=index_col,
                           na_values=na_values,
                           comment=comment)

        df1 = df.replace(np.nan, '', regex=True)
        return df1.T.to_dict(value_type)

    except FileNotFoundError as e:
        raise e
    except ValueError as e:
        raise e


def make_token():
    """
    Creates a cryptographically-secure, URL-safe string
    """
    return secrets.token_urlsafe(16)


def json_dumps(obj, indent=0):
    return json.dumps(obj, sort_keys=True, indent=4)


def check_duplicate(elems: list):
    for el in elems:
        if elems.count(el) > 1:
            return True
        return False


if __name__ == '__main__':

    import pprint
    pp = pprint.PrettyPrinter(indent=2)

    filename = 'D:\WorkSpace\_Projects\DJ_ETRI\Works\online_loader\conf\consume-mapping.xlsx'
    mapping = excel_to_dict(filename, index_col='table_name', value_type='dict')
    pp.pprint(mapping)
