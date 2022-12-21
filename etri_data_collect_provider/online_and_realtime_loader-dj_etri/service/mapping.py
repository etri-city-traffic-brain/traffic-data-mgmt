#!/usr/bin/env python
# -*- coding: utf-8 -*-


class MappingField:
    NUM = "num"
    KEY = "file_name"
    SCHEMA_NAME = "schema_name"
    PLATFORM_LINK = "platform_link"
    EXTRACT_TYPE = "extract_type"
    DATE_FIELD = "date_field"
    DATE_FORMAT = "db_date_format"


class KafkaHeader:
    FLAG = "FLAG"

    FLAG_START_TABLE = "START_TABLE"
    FLAG_START_FILE = "START_FILE"
    FLAG_END_TABLE = "END_TABLE"
    FLAG_DATA = "DATA"

    FILENAME = "FILENAME"