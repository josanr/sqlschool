# syntax = docker/dockerfile:1.2
FROM maven:3.6.3-jdk-11 as build

COPY pom.xml /pom.xml

COPY src/ /src/

RUN --mount=type=cache,target=/root/.m2 mvn clean compile assembly:single

FROM openjdk:11-jre-slim

COPY --from=build target/sqlschool-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

