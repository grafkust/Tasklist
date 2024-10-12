FROM maven:3.8.5-openjdk-18 AS build
WORKDIR /
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:21-oracle
WORKDIR /
COPY /src /src
COPY --from=build /target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
