FROM openjdk:11
# VOLUME /tmp
WORKDIR '/app'
COPY target/*.jar exam-portal-api.jar
ENTRYPOINT ["java","-jar","/exam-portal-api.jar"]