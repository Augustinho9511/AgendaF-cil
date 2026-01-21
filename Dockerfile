# Estágio de Build (Construção)
FROM gradle:7.6.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Constrói o projeto pulando os testes para ser mais rápido
RUN gradle build --no-daemon -x test

# Estágio de Execução
FROM openjdk:17-jdk-slim
EXPOSE 8080
# Copia o arquivo .jar gerado (o Gradle coloca dentro de build/libs)
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]