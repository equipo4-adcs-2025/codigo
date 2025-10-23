# Spring Boot Backend - Team 4

This is a backend application built with Spring Boot and Gradle for the course _Software Analysis, Design and Construction_ at Tecnológico de Monterrey.

## 📦 Package Structure

The base package for this project is: `mx.tec.mna.adcs.team4.backend`

## 🚀 How to Run the Application

### Prerequisites

- Java 17+
- Gradle (or use the included `gradlew` wrapper)
- Git (optional)
- Docker (optional)

### Options

#### 🐳 Option 1: Run with Docker (auto DB setup)

1. Ensure Docker is installed and running.
2. Run the backend and MySQL together:

```bash
./resume docker-run
```

3. The backend will be available at: `http://localhost:8080`

4. To stop the services:

```bash
./resume docker-stop
```

#### 🔧 Option 2: Run with Gradle (manual DB setup)

1. Clone or download the repository and move to the directory.
2. Ensure your `application.properties` is configured with a valid external database (e.g., MySQL).
3. Run the application:

```bash
./gradlew :app:bootRun
```

4. The application will start on: `http://localhost:8080`

### Health Check Endpoint

To verify the application is running, access: `GET /api/health`

Example:

```bash
curl http://localhost:8080/api/health
```

Response:

```json
{
  "status": "UP",
  "message": "Application is running"
}
```

## 🧪 Running Tests

```bash
./resume test
open app/build/reports/tests/test/index.html
open app/build/jacocoHtml/index.html
```

## 📚 Technologies Used

- Java 17
- Spring Boot 3.1.5
- Gradle
- JUnit 5
