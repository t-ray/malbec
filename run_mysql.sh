#!/bin/bash
docker run -d --name mysql-malbec \
  -e MYSQL_ROOT_PASSWORD=root \
  -v /Users/thutcheson/workspaces/malbec/mysql:/var/lib/mysql \
  -p 3306:3306 mysql:8
