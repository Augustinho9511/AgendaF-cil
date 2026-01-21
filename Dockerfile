# Estágio de Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
# Dá permissão e constrói o projeto
RUN chmod +x gradlew
RUN ./gradlew build -x test --no-daemon

# Estágio de Execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# O segredo está aqui: mudamos para /app/build/libs/
COPY --from=build /app/build/libs/*[!plain].jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]