#! /bin/sh
sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y apache-maven
git checkout master
git pull origin master

mvn --version
mvn package -Dmaven.test.ski=true
sudo docker-compose rm webserver
sudo docker-compose rm database

sudo docker-compose up --build

