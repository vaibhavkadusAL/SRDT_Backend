# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy all files into the container
COPY . .

# Build the app
RUN ./mvnw clean install

# Run the app
CMD ["java", "-jar", "target/srdt-backend.jar"]
