# Usar una imagen base de Java
FROM openjdk:17-jdk-slim

# Crear un directorio para la app
WORKDIR /app

# Copiar el jar generado por Maven
COPY target/curso-1.0-SNAPSHOT.jar app.jar

# Exponer el puerto (ajusta si tu app usa otro)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
