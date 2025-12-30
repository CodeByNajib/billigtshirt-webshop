# syntax=docker/dockerfile:1.6

# ========================
# STAGE 1: Build
# ========================
FROM maven:3.9.11-eclipse-temurin-21-alpine@sha256:922927df2c662cdd47ddb116443d6bec4696cfae3de1a0ddac8fcc7b87ce61ae AS builder
WORKDIR /app

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -B -ntp -DskipTests dependency:go-offline

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -ntp clean package -DskipTests

# ========================
# STAGE 2: Runtime
# ========================
FROM eclipse-temurin:21-jre-alpine@sha256:326837fba06a8ff5482a17bafbd65319e64a6e997febb7c85ebe7e3f73c12b11
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

# Copy den konkrete fat-jar
COPY --from=builder --chown=spring:spring /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

USER spring:spring
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
