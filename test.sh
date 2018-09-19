#!/bin/bash

export PROJ_NAME=brevity-test-project
lein clean
lein install
mkdir -p brevity-target
lein new brevity $PROJ_NAME --to-dir brevity-target/$PROJ_NAME
pushd brevity-target/$PROJ_NAME
lein cljsbuild once
lein brevity migrate
PORT=8081 lein test
