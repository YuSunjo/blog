./gradlew clean
./gradlew bootJar

scp -P22 ./blog-admin/build/libs/blog-admin-0.0.1-BLOG.jar root@115.71.238.146:~/blog/jar/blog-admin/build/libs/blog-admin-0.0.1-BLOG.jar

scp -P22 ./blog-api/build/libs/blog-api-0.0.1-BLOG.jar root@115.71.238.146:~/blog/jar/blog-api/build/libs/blog-api-0.0.1-BLOG.jar

ssh root@115.71.238.146 "./blog.jar.sh"