# Estágio de Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
# Copia todos os arquivos para o container
COPY . .
# Usa o wrapper do gradle (gradlew) que já está na sua pasta
RUN ./gradlew build -x test --no-daemon

# Estágio de Execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Remove o jar 'plain' antes de copiar para evitar conflitos
COPY --from=build /app/build/libs/*[!plain].jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]