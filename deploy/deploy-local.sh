docker ps -a

docker stop blog_admin
docker stop blog_api

docker rm blog_admin
docker rm blog_api

docker rmi blog_admin
docker rmi blog_api

docker-compose up -d --build