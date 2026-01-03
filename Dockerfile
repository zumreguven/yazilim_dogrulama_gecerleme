# --------------------------
# 1) Build stage
# --------------------------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Önce pom.xml ile bağımlılıkları indir (cache için)
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

# Kaynak kodları kopyala ve package oluştur
COPY . .
RUN mvn -B clean package -DskipTests

# --------------------------
# 2) Run stage
# --------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Build stage’den jar dosyasını al
COPY --from=build /app/target/*.jar app.jar

# Container port
EXPOSE 8080

# Uygulamayı çalıştır
ENTRYPOINT ["java","-jar","/app/app.jar"]
