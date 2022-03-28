#!/bin/sh

JAR_PWD=build/libs
CURRENT_PID_ADMIN=$(pgrep -f blog-admin-0.0.1-BLOG.jar)
echo CURRENT_PID

if [ -z $CURRENT_PID_ADMIN]
then
  echo "종료 할 것이 없음"
else
  echo "> kill -9 $CURRENT_PID_ADMIN"
  kill -9 $CURRENT_PID_ADMIN
  sleep 5
fi
echo "> 배포 시작"
nohup java -jar ~/blog/jar/blog-admin-0.0.1-BLOG.jar --spring.profiles.active=dev > /logs/blog_admin.log 2>&1 &

CURRENT_PID_API=$(pgrep -f blog-api-0.0.1-BLOG.jar)
echo CURRENT_PID

if [ -z $CURRENT_PID_API]
then
  echo "종료 할 것이 없음"
else
  echo "> kill -9 $CURRENT_PID_API"
  kill -9 $CURRENT_PID_API
  sleep 5
fi
echo "> 배포 시작"
nohup java -jar ~/blog/jar/blog-api-0.0.1-BLOG.jar --spring.profiles.active=dev > /logs/blog_api.log 2>&1 &