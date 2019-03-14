FROM openjdk:8-jdk-alpine
COPY target/*.jar my-lock-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/my-lock-0.0.1-SNAPSHOT.jar"]
#测试回退

