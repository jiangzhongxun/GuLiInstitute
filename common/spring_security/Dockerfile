FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/spring_security-0.0.1-SNAPSHOT.jar spring_security-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/spring_security-0.0.1-SNAPSHOT.jar", "&"]