FROM openjdk:26-jdk-slim
LABEL authors="ankitchobdar"

# Application working directory
WORKDIR /app

# Create directory for the application JAR
RUN mkdir -p /app/lib

# Copy the built JAR into the image (adjust the source path if different)
# Assumes the JAR is at target/*.jar after building the project
COPY target/*.jar /app/lib/app.jar

# Expose application port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app/lib/app.jar"]
