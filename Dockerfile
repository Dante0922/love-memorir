FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

COPY build/libs/server-1.0.jar Server.jar

ENTRYPOINT ["java", "-jar", "Server.jar"]