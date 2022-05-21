#!/bin/bash

INSTALL=install

set -x

mvn clean
rm -r $INSTALL
mvn install
mvn dependency:copy-dependencies -DincludeScope=runtime -DoutputDirectory=$INSTALL
cp target/*.jar $INSTALL
