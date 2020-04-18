#!/bin/bash
#./buildnatives.sh
./buildjars.sh
status=$?
if test $status -eq 0
then
	./buildcontainers_and_up.sh
fi
