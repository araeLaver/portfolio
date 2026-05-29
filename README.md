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

### 원클릭 운영 전환

```bash
cd /opt/portfolio
cp scripts/portfolio.env.example .portfolio.env

# .portfolio.env에 DATABASE_URL / ADMIN_PASSWORD / PORTFOLIO_BASE_URL 등 채우기

chmod +x scripts/*.sh
./scripts/cutover-macmini.sh
```

`cutover-macmini.sh`는 아래를 자동 수행합니다.

- `.portfolio.env` 로드 및 필수 변수 검증
- `deploy-macmini.sh` 실행 (빌드/재시작)
- 공개 URL 기준 헬스체크
- `PORTFOLIO_AUTO_LAUNCHD=1`이면 launchd 등록까지 자동 수행 (기본값: 1)

launchd만 원할 때는 아래와 같이 비활성화할 수 있습니다.

```bash
PORTFOLIO_AUTO_LAUNCHD=0 ./scripts/cutover-macmini.sh
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

### Koyeb 연결 끊기 (오프보드)

Mac mini로 서비스 이전이 끝난 뒤 아래 순서대로 정리합니다.

1. DNS/트래픽 전환 점검
   - 최종 공개 주소가 맥미니로 트래픽을 받는지 확인
   - `curl -fsS https://portfolio.example.com/actuator/health`

2. `.portfolio.env` 영구화
   - 맥미니 운영 환경변수(`PORTFOLIO_BASE_URL`, `*_LIVE_URL`)를 최종 도메인으로 고정
   - `PORTFOLIO_AUTO_LAUNCHD=0` 실행은 배포 직후 1회 확인용, 운영 중엔 `1` 유지 또는 launchd 스크립트 직접 관리

3. 기존 Koyeb 리소스 정리
   - Koyeb 앱/서비스를 중단 또는 삭제
   - GitHub Actions/Koyeb 연결 Webhook 또는 배포 토큰이 남아있다면 제거
   - Koyeb가 참조하던 DB 접속 정보/시크릿은 비활성화

4. 운영 모니터링 전환
   - `launchctl list | grep com.portfolio.macmini`로 재부팅 후 자동 시작 상태 확인
   - 로그: `tail -f logs/portfolio.log`
   - 필요 시 DNS TTL을 서서히 낮춘 뒤 변경

5. 안전성 검증
   - 외부 주소 기반 모든 메뉴/버튼 링크가 정상 응답하는지 확인
   - 기본 관리자 계정으로 로그인 및 주요 기능 동작 점검

상세 실행 항목이 필요하면 다음 문서를 따르세요.

- [KOYEB_OFFBOARDING.md](/Volumes/WorkDrive/Develop/19_portfolio/portfolio/KOYEB_OFFBOARDING.md)

### External address 변경 예시

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
