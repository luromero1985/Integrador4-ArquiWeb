# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Definir un volumen temporal para la aplicación
VOLUME /tmp

# Copiar el archivo .jar de la aplicación al contenedor
COPY target/microusuario.jar microusuario.jar

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "microusuario.jar"]
