#!/bin/bash

docker exec docker_md-api-gateway_1 \
curl -i -X POST \
--url 127.0.0.1:8001/services/ \
--data 'name=md-auth' \
--data 'host=md-auth' \
--data 'url=http://md-auth:8080/'

docker exec docker_md-api-gateway_1 \
curl -i -X POST \
--url http://localhost:8001/services/md-auth/routes \
--data 'paths[]=/auth'


docker exec docker_md-api-gateway_1 \
curl -i -X POST \
--url 127.0.0.1:8001/services/ \
--data 'name=md-users' \
--data 'host=md-users' \
--data 'url=http://md-users:8080/'

docker exec docker_md-api-gateway_1 \
curl -i -X POST \
--url http://localhost:8001/services/md-users/routes \
--data 'paths[]=/users'