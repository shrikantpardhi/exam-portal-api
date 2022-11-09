FROM maven:latest AS maven_build
COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package

FROM amazoncorretto:11

#maintainer 
MAINTAINER shripardhi92@gmail.com

#expose port 8080
EXPOSE 8080

CMD java -jar /data/hello-world-0.1.0.jar

#copy hello world to docker image from builder image

COPY --from=maven_build /tmp/target/exam-portal-0.0.1.jar /data/exam-portal-0.0.1.jar
