FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api-gateway-0.0.1-SNAPSHOT.jar", "&"]