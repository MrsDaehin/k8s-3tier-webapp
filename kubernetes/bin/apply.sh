#!/bin/sh
set -eu

./apply-default-quarkus.sh
./apply-default-option.sh
./apply-default-openliberty.sh
./apply-default-spring.sh
./apply-monitoring.sh
