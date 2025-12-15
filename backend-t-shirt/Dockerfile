# ==========================================
# STAGE 1: Build Stage
# ==========================================
# Vi bruger et 'Multi-stage build' for at holde det endelige image småt.
# Base Image: Maven på Eclipse Temurin 21 (Alpine Linux).
# Hashpinning: Vi låser versionen med SHA256 for at sikre os mod Supply Chain Attacks.
FROM maven:3.9.11-eclipse-temurin-21-alpine@sha256:922927df2c662cdd47ddb116443d6bec4696cfae3de1a0ddac8fcc7b87ce61ae AS builder

WORKDIR /app

# 1. Optimering af cache:
# Vi kopierer kun pom.xml først og henter dependencies.
# Dette lagres i Docker cache, så vi ikke skal hente internettet ned, hvis kun koden ændres.
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Byg applikationen:
COPY src ./src
RUN mvn clean package -DskipTests

# 3. Spring Boot Layering:
# Vi udpakker jar-filen i lag (dependencies, loader, snapshot, application).
# Det gør fremtidige builds og uploads til GHCR meget hurtigere.
RUN java -Djarmode=layertools -jar target/*.jar extract

# ==========================================
# STAGE 2: Runtime Stage
# ==========================================
# Her skifter vi til et rent JRE (Java Runtime Environment) image.
# Det indeholder IKKE compiler-værktøjer (javac, mvn), hvilket gør det mere sikkert.
FROM eclipse-temurin:21-jre-alpine@sha256:326837fba06a8ff5482a17bafbd65319e64a6e997febb7c85ebe7e3f73c12b11

WORKDIR /app

# Sikkerhed: Opretter en dedikeret bruger, så appen ikke kører som 'root'.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Kopierer de udpakkede lag fra builder-stadiet.
# Dette sikrer, at vi genbruger layers optimalt.
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

EXPOSE 8080

# Bruger JarLauncher til at starte de udpakkede lag korrekt.
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
