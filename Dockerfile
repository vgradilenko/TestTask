FROM openjdk:8
ADD api/target/api-0.0.1-SNAPSHOT-spring-boot.jar app.jar
RUN apt-get update && apt-get install mysql-server -y \
&& service mysql start
ADD initDb.sh /docker-entrypoint-initdb.d/initDb.sh
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]