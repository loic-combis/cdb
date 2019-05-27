#! /bin/sh
sudo docker-compose rm webserver
sudo docker-compose rm database

sudo docker-compose up --build

