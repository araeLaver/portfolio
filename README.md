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
- Mac mini self-hosted deployment
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

### Production Deployment (Mac Mini)

Set runtime environment variables:

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/portfolio?currentSchema=java_portfolio
export PGUSER=portfolio
export DATABASE_PASSWORD=<password>
export PORTFOLIO_BASE_URL=https://portfolio.example.com
export BEAM_LIVE_URL=https://portfolio.example.com
export TRAVELMATE_LIVE_URL=https://portfolio.example.com
export IDEA_MANAGER_LIVE_URL=https://portfolio.example.com
export ADMIN_USERNAME=admin
export ADMIN_PASSWORD=<secure-password>
```

Build and deploy:

```bash
./scripts/deploy-macmini.sh
```

Health check:

```bash
curl -fsS https://portfolio.example.com/actuator/health
```

### Mac mini 운영 체크포인트

- Create environment variables file

```bash
cd /opt/portfolio
cp scripts/portfolio.env.example .portfolio.env
```

- Fill secrets and deploy

```bash
chmod +x scripts/*.sh
./scripts/deploy-macmini.sh
```

Optional environment for script:

```bash
export PORTFOLIO_APP_DIR=/opt/portfolio
export PORTFOLIO_ENV_FILE=/opt/portfolio/.portfolio.env
export PORTFOLIO_JAVA_BIN=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java
```

- Keep running after reboot (launchd)

```bash
./scripts/install-launchd-macmini.sh
launchctl list | grep com.portfolio.macmini
```

- Remove launchd service (if needed)

```bash
./scripts/uninstall-launchd-macmini.sh
```

- Runtime checks

```bash
./scripts/healthcheck-macmini.sh https://portfolio.example.com
tail -n 120 logs/portfolio.log
```

### External address (Koyeb 탈거) 예시

1) 라우터 포트포워딩: `tcp 80/443 -> 맥미니` + 도메인 A/AAAA 레코드

2) Cloudflare Tunnel (권장):  
`cloudflared tunnel route dns`로 `portfolio.example.com`을 맥미니로 연결

DNS가 바뀌면 아래 환경변수만 갱신 후 재배포하면 됩니다.

- `PORTFOLIO_BASE_URL`
- `BEAM_LIVE_URL`
- `TRAVELMATE_LIVE_URL`
- `IDEA_MANAGER_LIVE_URL`

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `ADMIN_USERNAME` | Admin panel username | `admin` |
| `ADMIN_PASSWORD` | Admin panel password | - |
| `DATABASE_URL` | PostgreSQL connection string | - |
| `PORTFOLIO_BASE_URL` | Canonical public domain for links | `http://localhost:8080` |

## License

© 2025 Developer Portfolio. All Rights Reserved.
