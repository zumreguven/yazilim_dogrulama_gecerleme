# 1) Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache için önce pom
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

# Sonra kaynaklar
COPY . .
RUN mvn -B clean package -DskipTests

# 2) Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# jar ismi farklıysa burada wildcard iş görür
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
