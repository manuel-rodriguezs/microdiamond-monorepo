#!/bin/bash
cd commons
./mvnw clean install
status=$?
cd ..
if test $status -ne 0
then
    exit $status
fi
for d in microdiamond-server*/ ; do
    cd "$d"
    ./mvnw clean package
    status=$?
    cd ..
    if test $status -ne 0
    then
        exit $status
    fi
done