# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy everything
COPY . .

# Ensure Maven wrapper is executable
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean install -DskipTests

# Run the JAR (adjust the jar name as needed)
CMD ["java", "-jar", "target/srdt-backend.jar"]
