# -*- coding: utf-8 -*-

import os
import pathlib
import fnmatch
import pprint

pp = pprint.PrettyPrinter(indent=2)


def get_file_list(path, patt):
    matches = []
    for dirpath, subdirs, filenames in os.walk(path):
        for filename in fnmatch.filter(filenames, patt):
            matches.append(os.path.join(dirpath, filename))
    matches.sort()
    return matches


def get_file_list_excluded(base_path, file_pattern, file_exclude_pattern):
    matches = []
    for dirpath, subdirs, filenames in os.walk(base_path):
        for filename in fnmatch.filter(filenames, file_pattern):
            matches.append(os.path.join(dirpath, filename))

    excludeds = []
    for dirpath, subdirs, filenames in os.walk(base_path):
        for filename in fnmatch.filter(filenames, file_exclude_pattern):
            excludeds.append(os.path.join(dirpath, filename))

    paths = list(set(matches) - set(excludeds))
    paths.sort()
    return paths


if __name__ == '__main__':
    paths = get_file_list_excluded('D:\WorkSpace\_Projects\DJ_ETRI\Works\online_loader\_REF\sample\data\ODS',
                                   '*.csv',
                                   '*result*')
