#!/bin/sh
API_DOCKER_IMAGE=ghcr.io/yusunjo/blog/api
API_DOCKER_NAME=blog-api
ADMIN_DOCKER_IMAGE=ghcr.io/yusunjo/blog/admin
ADMIN_DOCKER_NAME=blog-admin

cat ~/deploy/GIT-TOKEN.txt | docker login ghcr.io -u yusunjo --password-stdin

docker stop $API_DOCKER_NAME && docker rm -fv $API_DOCKER_NAME && docker rmi -f $API_DOCKER_IMAGE:latest
docker run -d -p 8080:8080 --name $API_DOCKER_NAME --restart always $API_DOCKER_IMAGE:latest

docker stop $ADMIN_DOCKER_NAME && docker rm -fv $ADMIN_DOCKER_NAME && docker rmi -f $ADMIN_DOCKER_IMAGE:latest
docker run -d -p 9000:9000 --name $ADMIN_DOCKER_NAME --restart always $ADMIN_DOCKER_IMAGE:latest