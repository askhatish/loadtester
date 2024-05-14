# Loadtester
This is a simple HTTP Load Testing project. It allows users easily load test their HTTP endpoints.
It supports generating concurrent HTTP requests at a specified QPS rate, collecting latency metrics, and tracking error rates.

# Prerequisits
- Java 21
- Gradle 8.7

# Basic commands
- Building the project: `./gradlew clean build`
- Running unit tests: `./gradlew test`
- Launching locally: `./gradlew :app:run --args="<url> <method> <qps> <duration>"`

# Docker
- Build image: `docker build -t loadtester .`
- Run the container with the built image: `docker run -it loadtester --args='https://www.google.com GET 10 30'`

# API
The API for calling the loadtest is in the `org.loadtest.api` package. The load test is conducted via
the `org.loadtest.api.Loadtest`, where the user must define the request generator class with type `ILoadtestRequestGenerable`
and the `LoadTestConfig` class. The main method is called `run` returns LoadTestMetrics, which contains total requests, total erros,
error ratio, average latency, and percentiles by latency. Happy load testing!


