# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy all files into the container
COPY . .

# Make Maven wrapper executable (for Linux)
RUN chmod +x mvnw

# Build the Spring Boot project
RUN ./mvnw clean install -DskipTests

# Run the generated JAR
CMD ["java", "-jar", "target/contact-0.0.1-SNAPSHOT.jar"]
