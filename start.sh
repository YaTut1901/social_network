#!/bin/bash
NETWORK=transactions-network 1
MYSQL_ALIAS=mysql 
MYSQL_PASSWORD=1234
DATABASE=mysql
MYSQL_IMAGE=mysql:5.7
DOCKER_PROFILE=ioberid
DOCKER_TRANSACTIONS_TAG=transactions
docker build . -t $DOCKER_PROFILE/$DOCKER_TRANSACTIONS_TAG && docker network create $NETWORK
docker run -d --network $NETWORK --network-alias $MYSQL_ALIAS -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD -e MYSQL_DATABASE=$DATABASE $MYSQL_IMAGE
docker run -it --network $NETWORK $DOCKER_PROFILE/$DOCKER_TRANSACTIONS_TAG
