#!/bin/bash
DIR="$( dirname $0 )"
ROOT_DIR="$DIR/.."

java -cp $DIR/h2*.jar org.h2.tools.RunScript -url jdbc:h2:file:$ROOT_DIR/target/db -script $ROOT_DIR/src/main/dbConf/schema.sql -user "sa"
