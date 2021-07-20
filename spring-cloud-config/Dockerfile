FROM openjdk:17-ea-11-jdk-slim
VOLUME /app
COPY build/libs/spring-cloud-config-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]