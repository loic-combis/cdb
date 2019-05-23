#! /bin/sh
# Recreate package 
mvn clean package -Dmaven.test.skip=true

sudo docker-compose rm webserver
sudo docker-compose rm database

sudo docker-compose up --build

