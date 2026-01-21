# Estágio de Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
# Dá permissão e gera APENAS o jar executável do Spring Boot
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test --no-daemon

# Estágio de Execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o único JAR gerado pelo comando bootJar
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]