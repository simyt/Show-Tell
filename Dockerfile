FROM openjdk:21-jdk-bullseye

WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN chmod +x ./mvnw

RUN ./mvnw package -Dmaven.test.skip=true

ENV WEATHER_KEY=abc123 PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/Show-Tell-0.0.1-SNAPSHOT.jar