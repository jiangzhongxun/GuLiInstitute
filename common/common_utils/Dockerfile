FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/common_utils-0.0.1-SNAPSHOT.jar common_utils-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/common_utils-0.0.1-SNAPSHOT.jar", "&"]