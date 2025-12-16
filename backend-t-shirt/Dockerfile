# ========================
# STAGE 1: Build Stage
# ========================
# Vi bruger hashpinning (@sha256:...) for at sikre os mod Supply Chain Attacks.
# Dette garanterer, at vi bygger på PRÆCIS samme kode hver gang.
FROM maven:3.9.11-eclipse-temurin-21-alpine@sha256:922927df2c662cdd47ddb116443d6bec4696cfae3de1a0ddac8fcc7b87ce61ae AS builder

WORKDIR /app

# OPTIMERING: Dependency Caching
# Vi henter dependencies før koden kopieres.
# Docker cacher dette lag, så vi ikke downloader internettet ved hver lille kodeændring.
COPY pom.xml .
# RUN mvn dependency:go-offline

# Byg applikationen
COPY src ./src
RUN mvn clean package -DskipTests

# ========================
# STAGE 2: Runtime Stage
# ========================
# Vi bruger også hashpinning på vores runtime image for maksimal sikkerhed.
FROM eclipse-temurin:21-jre-alpine@sha256:326837fba06a8ff5482a17bafbd65319e64a6e997febb7c85ebe7e3f73c12b11

WORKDIR /app

# SIKKERHED: Non-root user
# Vi opretter en bruger 'spring' for at følge 'Least Privilege' princippet.
# Hvis containeren hackes, har angriberen ikke root-adgang.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Kopierer JAR-filen fra builder-staget
COPY --from=builder /app/target/*.jar app.jar

# Fortæller Spring Boot at den skal bruge 'application-prod.properties'
ENV SPRING_PROFILES_ACTIVE=prod

# Starter applikationen
ENTRYPOINT ["java", "-jar", "app.jar"]