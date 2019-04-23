FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/libra-2.0.0.jar libra.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/libra.jar"]