FROM openjdk:21-oracle
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
