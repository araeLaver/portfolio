# Developer Portfolio

Modern portfolio website showcasing full-stack development projects with real-time features, security implementations, and cloud deployments.

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.3
- Spring Security
- Spring Data JPA
- PostgreSQL / H2

### Frontend
- Thymeleaf
- Modern CSS3 (Dark Mode Design)
- Responsive Layout

### Infrastructure
- Docker
- Koyeb Deployment
- Git CI/CD

## Features

- Dynamic project showcase with markdown support
- Admin panel for content management
- Secure authentication with BCrypt
- XSS protection and security headers
- Responsive design optimized for all devices
- Health monitoring endpoints

## Projects

This portfolio showcases three major projects:

1. **BEAM** - Global Security Messenger with WebSocket real-time communication
2. **TravelMate** - Travel companion matching platform with OAuth2 integration  
3. **Idea Manager** - AI-powered idea management system with kanban boards

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL (for production) or H2 (for development)

### Development Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Access at: `http://localhost:8080`

### Production Deployment

Configure PostgreSQL connection in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://host:port/database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Build and run:

```bash
mvn clean package
java -jar target/portfolio-app-0.0.1-SNAPSHOT.jar
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `ADMIN_USERNAME` | Admin panel username | `admin` |
| `ADMIN_PASSWORD` | Admin panel password | - |
| `DATABASE_URL` | PostgreSQL connection string | - |

## License

© 2025 Developer Portfolio. All Rights Reserved.
