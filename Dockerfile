# Usar uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR para o contêiner
COPY target/workshopmongo-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para executar o aplicativo
CMD ["java", "-jar", "app.jar"]
