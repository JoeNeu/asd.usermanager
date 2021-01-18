FROM openjdk:11-jdk
LABEL maintainer="johannes.neustaedter@stud.fh-campuswien.ac.at"
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
