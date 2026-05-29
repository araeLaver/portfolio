# Koyeb 오프보드(연결 해제) 체크리스트

맥미니 이전이 완료된 뒤 Koyeb 연결을 안전하게 끊기 위한 실행 순서입니다.

## 1) 배포 전 점검

- 공개 주소가 맥미니로 노출되는지 확인
  - `curl -fsS https://portfolio.example.com/actuator/health`
  - 주요 페이지/관리자 URL 기본 동작 확인
- Koyeb에서 사용하는 최종 환경변수 값 확인
  - 공개 주소 관련 변수(예: `PORTFOLIO_BASE_URL`, `BEAM_LIVE_URL`, `TRAVELMATE_LIVE_URL`, `IDEA_MANAGER_LIVE_URL`)를 맥미니 값으로 맞춤
- GitHub Actions/외부 webhook/봇이 아직 Koyeb를 배포 대상으로 두고 있는지 확인

## 2) Koyeb 앱/서비스 정지

- Koyeb 콘솔에 로그인
- 해당 서비스에서 **Deployments/Versions** 기록 저장(롤백용 확인용)
- 서비스 상태를 `Stop` 또는 `Pause`로 전환
- 트래픽 라우팅이 0인지 확인(필요 시 1회 헬스체크로 검증)

## 3) 도메인 및 라우팅 이전

- Koyeb에 등록된 커스텀 도메인/라우팅 레코드 해제
- DNS TTL을 낮춘 뒤 기존 DNS 레코드를 맥미니(포트/터널) 쪽으로 전환
- 전환 후 5~10분 간격으로 아래 확인 반복
  - `curl -fsS https://portfolio.example.com/actuator/health`
  - 포트폴리오 내 링크 3개 프로젝트 URL 동작 확인

## 4) 시크릿·환경변수 정리

- Koyeb DB/앱 시크릿 또는 환경변수에서 불필요 항목 비활성/삭제
- 연결용 서비스 토큰(API token, deployment token) 폐기
- 저장소/문서에 Koyeb용 민감값이 남아 있지 않은지 검색
  - `git grep -n "koyeb|Koyeb|KOYEB"` (저장소 민감정보가 없는지 점검)

## 5) 데이터 이전 완료 검증

- Mac mini DB 백업/복구 상태 확인
- 운영 사용자 계정 로그인/게시물 CRUD/프로젝트 링크 동작 확인
- 로그 모니터링
  - `tail -f logs/portfolio.log`
- launchd 자동 시작 상태 확인
  - `launchctl list | grep com.portfolio.macmini`

## 6) 최종 정리

- Koyeb 앱 삭제 (정상 동작이 확인된 후)
- 관련 요금제/리소스 종료로 과금 정지
- README의 오프보드 체크리스트와 현재 운영 주소를 최종 확정

## 롤백 원칙

- 트래픽 이슈 발생 시: Koyeb DNS/라우팅만 즉시 이전 상태로 되돌린 뒤,
  - 맥미니 로그와 `cutover` 로그, 헬스체크 결과를 붙여 원인 분석
  - 10분 이내 원인 파악 가능한지 점검
