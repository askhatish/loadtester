# Loadtester
This is a simple HTTP Load Testing project. It allows users easily load test their HTTP endpoints.
It supports generating concurrent HTTP requests at a specified QPS rate, collecting latency metrics, and tracking error rates.

# Prerequisits
- Java 21
- Gradle 8.7

# Basic commands
- Building the project: `./gradlew clean build`
- Running unit tests: `./gradlew :test`
- Launching locally: `./gradlew :app:run --args="https://www.google.com 10 30 10"`

# API



