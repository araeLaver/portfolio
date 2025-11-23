# Contributing Guide

본 프로젝트는 체계적인 Git 워크플로우를 따릅니다.

## 브랜치 전략

```
main          # 운영 환경 (Koyeb 자동 배포)
  ↑
develop       # 개발 통합 브랜치
  ↑
feature/*     # 기능 개발 브랜치
hotfix/*      # 긴급 수정 브랜치
```

## 개발 워크플로우

### 1. 새 기능 개발

```bash
# develop 브랜치에서 시작
git checkout develop
git pull origin develop

# 기능 브랜치 생성
git checkout -b feature/기능명

# 개발 작업 수행
# ...

# 커밋 (Conventional Commits 규칙 준수)
git add .
git commit -m "feat: 기능 설명"

# 원격 저장소에 푸시
git push origin feature/기능명
```

### 2. Pull Request 생성

1. GitHub에서 `feature/기능명` → `develop` PR 생성
2. PR 템플릿에 따라 내용 작성
3. CI 체크 통과 확인
4. Self-review 후 Merge

### 3. develop 테스트 및 배포

```bash
# develop 브랜치에서 로컬 테스트
git checkout develop
git pull origin develop
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 테스트 확인 후 운영 배포 준비
# GitHub에서 develop → main PR 생성
# CI/CD 파이프라인 통과 확인 후 Merge
# Koyeb 자동 배포 트리거
```

### 4. 긴급 수정 (Hotfix)

```bash
# main 브랜치에서 직접 분기
git checkout main
git pull origin main
git checkout -b hotfix/이슈명

# 수정 작업
git add .
git commit -m "fix: 긴급 수정 내용"

# main과 develop 양쪽에 병합
git checkout main
git merge hotfix/이슈명
git push origin main

git checkout develop
git merge hotfix/이슈명
git push origin develop

# hotfix 브랜치 삭제
git branch -d hotfix/이슈명
```

## 커밋 메시지 규칙 (Conventional Commits)

```
<type>: <subject>

<body> (선택)
```

### Type 종류
- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `refactor`: 리팩토링
- `style`: 코드 포맷팅, 세미콜론 누락 등
- `test`: 테스트 코드 추가
- `docs`: 문서 수정
- `chore`: 빌드 설정, 패키지 매니저 수정
- `perf`: 성능 개선

### 예시
```bash
git commit -m "feat: Add project search functionality"
git commit -m "fix: Resolve admin login authentication bug"
git commit -m "refactor: Improve ProjectService code structure"
git commit -m "docs: Update README with deployment instructions"
```

## 코드 품질 기준

### 빌드 및 테스트
```bash
# 빌드 성공 필수
mvn clean install

# 테스트 통과 필수
mvn test

# 코드 스타일 체크
mvn checkstyle:check
```

### 보안 체크리스트
- [ ] XSS 취약점 확인
- [ ] SQL Injection 방지
- [ ] 인증/인가 로직 검증
- [ ] 민감 정보 하드코딩 금지
- [ ] OWASP Top 10 검토

### 성능 고려사항
- [ ] N+1 쿼리 문제 확인
- [ ] 불필요한 데이터 로딩 방지
- [ ] 적절한 인덱싱 적용
- [ ] 캐싱 전략 검토

## CI/CD 파이프라인

### develop 브랜치 Push/PR
1. Build with Maven
2. Run unit tests
3. Code style check
4. Generate build artifact

### main 브랜치 Merge
1. Build with Maven
2. Run unit tests
3. Deploy to Koyeb
4. Health check

## 로컬 개발 환경

### 개발 모드 실행
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 운영 모드 테스트
```bash
# PostgreSQL 설정 필요
export DATABASE_URL=jdbc:postgresql://localhost:5432/portfolio
export ADMIN_PASSWORD=your_password
mvn spring-boot:run
```

## 배포 환경

### Koyeb 환경 변수
- `ADMIN_USERNAME`: Admin 계정 아이디
- `ADMIN_PASSWORD`: Admin 계정 비밀번호 (필수)
- `DATABASE_URL`: PostgreSQL 연결 문자열

### 자동 배포 트리거
- `main` 브랜치에 Merge 시 자동 배포
- Health check: `/actuator/health`
- 배포 완료 후 수동 검증 필요

## 문제 해결

### 빌드 실패
```bash
# 의존성 재설치
mvn clean install -U

# 캐시 삭제
mvn dependency:purge-local-repository
```

### 테스트 실패
```bash
# 특정 테스트만 실행
mvn test -Dtest=ClassName#methodName

# 테스트 스킵 (비추천)
mvn clean install -DskipTests
```

## 리뷰 가이드라인

### Self-review 체크리스트
- [ ] 코드 중복 제거
- [ ] 변수/함수명이 명확한가?
- [ ] 주석이 필요한 복잡한 로직 설명
- [ ] 불필요한 주석 제거
- [ ] 로그 레벨 적절한가? (DEBUG, INFO, WARN, ERROR)
- [ ] 예외 처리 누락 없는가?
- [ ] 리소스 누수 없는가? (DB 커넥션, 파일 핸들)

## 참고 자료

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/)
- [GitHub Actions](https://docs.github.com/en/actions)
