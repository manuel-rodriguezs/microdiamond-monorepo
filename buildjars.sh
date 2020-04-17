#!/bin/bash
for d in microdiamond-server*/ ; do
    cd "$d"
    ./mvnw clean package
    status=$?
    echo $status
    cd ..
    if test $status -ne 0
    then
        exit $status
    fi
done