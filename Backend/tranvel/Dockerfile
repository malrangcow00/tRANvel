FROM openjdk:21-jdk-alpine
VOLUME /tmp
ADD ./target/tranvel-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app.jar"]