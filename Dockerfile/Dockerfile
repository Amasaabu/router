FROM  ubuntu:20.04 AS build
RUN apt-get update -y && apt-get install maven -y
RUN apt install openjdk-21-jdk-headless -y
COPY . app
WORKDIR app
RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim
ENV DB_USER="root"
ENV DB_URL="jdbc:mysql://host.docker.internal:3306/middlewaredb"
ENV DB_PASS=""
ENV DHMAC_SEC="aG+KbPdSgVkYp3s6v9y\$B&E)H@McQfThWmZq4t7w!z%C*F-JaNdRgUkXn2r5u8x/A"
COPY --from=build /app/target/routing-0.0.1-SNAPSHOT.jar /app/routing.jar
WORKDIR /app
EXPOSE 8081

CMD ["java", "-jar", "routing.jar"]