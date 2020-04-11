#!/bin/bash
./buildnatives.sh 
status=$?
if test $status -eq 0
then
	docker-compose -f docker/docker-compose.yml build
	status=$?
	if test $status -eq 0
	then
		docker-compose -f docker/docker-compose.yml up
	fi
fi
