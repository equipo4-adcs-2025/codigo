# Spring Boot Backend - Team 4

This is a backend application built with Spring Boot and Gradle for the course _Software Analysis, Design and Construction_ at Tecnológico de Monterrey.

## 📦 Package Structure

The base package for this project is: `mx.tec.mna.adcs.team4.backend`

## 🚀 How to Run the Application

### Prerequisites

- Java 17+
- Gradle (or use the included `gradlew` wrapper)
- Git (optional)

### Steps

1. Clone the repository move to the directory
2. Run the application using Gradle:

```bash
./gradlew bootRun
```

3. The application will start on: `http://localhost:8080`

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
./gradlew test
```

## 📚 Technologies Used

- Java 17
- Spring Boot 3.1.5
- Gradle
- JUnit 5
