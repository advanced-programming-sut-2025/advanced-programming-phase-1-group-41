FROM eclipse-temurin:17
COPY your-server.jar /app/server.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "server.jar"]
