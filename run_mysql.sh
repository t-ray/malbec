#!/bin/bash
#
# This script just creates a new mysql container with simple username/password
# semantics
docker run -d --name mysql-malbec \
  -e MYSQL_ROOT_PASSWORD=root \
  -v /$(PWD)/mysql:/var/lib/mysql \
  -p 3306:3306 mysql:8
