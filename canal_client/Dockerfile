FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/canal_client-0.0.1-SNAPSHOT.jar canal_client-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/canal_client-0.0.1-SNAPSHOT.jar", "&"]