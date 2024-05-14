# Use an official OpenJDK base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project directory into the container
COPY . .

# Build the Gradle project
RUN ./gradlew :app:build --no-daemon

# Expose the port your application runs on (if applicable)
# EXPOSE 8080

# Set the entry point for the container
ENTRYPOINT ["./gradlew", ":app:run"]