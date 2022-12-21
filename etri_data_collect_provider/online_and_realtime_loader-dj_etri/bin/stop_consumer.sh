#!/bin/bash

pgrep -f consumer_main.py | xargs kill -TERM
