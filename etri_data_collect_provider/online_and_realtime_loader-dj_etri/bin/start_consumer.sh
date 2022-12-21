#!/bin/bash

APP_HOME=$(realpath $(dirname $0)/../)
PYTHON_BIN=/home/dj_etri/sdks/anaconda3/envs/dj_etri/bin/python

nohup $PYTHON_BIN $APP_HOME/consumer_main.py > /dev/null 2>&1 &

