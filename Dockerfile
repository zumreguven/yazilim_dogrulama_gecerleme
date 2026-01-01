# Build aşaması
FROM eclipse-temurin:17-jdk-jammy AS build

# Maven ve gerekli araçlar
RUN apt-get update && \
    apt-get install -y maven curl git && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY pom.xml .

# Bağımlılıkları önceden indir
RUN mvn dependency:go-offline

COPY src /app/src

# Paketle (testleri atla)
RUN mvn package -DskipTests

# Çalışma aşaması
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Build aşamasından jar dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-Dserver.port=8081", "-jar", "app.jar"]
