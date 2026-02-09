package com.portfolio.config;

import com.portfolio.entity.ProjectEntity;
import com.portfolio.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(String... args) throws Exception {
        long existingCount = projectRepository.count();
        if (existingCount > 0) {
            logger.info("Database already contains {} projects. Checking for updates...", existingCount);
            updateProjectUrls();
            ensureDevOrchestraExists();
            ensureDisplayOrder();
            return;
        }

        logger.info("No projects found. Initializing database with portfolio projects...");

        // 1. DevOrchestra - Claude Code Multi-Terminal Framework (맨 앞)
        ProjectEntity devOrchestra = createDevOrchestra();
        devOrchestra.setDisplayOrder(1);

        // 2. BEAM - Global Security Messenger
        ProjectEntity beam = new ProjectEntity();
        beam.setId("beam-messenger");
        beam.setTitle("BEAM - 글로벌 보안 메신저");
        beam.setDescription("WebSocket 기반 실시간 메신저. 1:1 채팅, 그룹 채팅(100명), 파일 공유, JWT 인증, End-to-End 암호화 지원.");
        beam.setGitUrl("https://github.com/araeLaver/chat.git");
        beam.setLiveUrl("https://chat-untab-fddd496d.koyeb.app/");
        beam.setStack(Arrays.asList("Java 17", "Spring Boot 3.2", "WebSocket (STOMP)", "Spring Security", "PostgreSQL", "Docker", "Koyeb"));
        beam.setDetails("# BEAM - Global Security Messenger\n\n## 주요 기능\n\n### 실시간 메시징\n- 1:1 다이렉트 메시지\n- 그룹 채팅 (최대 100명)\n- 타이핑 인디케이터\n- 온라인 상태 표시\n- 읽음 확인\n- 메시지 검색\n\n### 소셜\n- 친구 요청/수락/거절/차단\n- 친구 목록 관리\n- 프로필 및 상태 메시지\n\n### 파일 공유\n- 최대 10MB 파일 업로드\n- 이미지/비디오 썸네일 자동 생성\n- 다양한 파일 형식 지원\n- 다운로드 내역 추적\n\n### 보안\n- JWT 토큰 인증\n- BCrypt 비밀번호 암호화\n- End-to-End 암호화 프레임워크\n- 전화번호 인증\n\n## 기술 스택\n\n### Backend\n- Java 17, Spring Boot 3.2\n- WebSocket (STOMP + SockJS)\n- Spring Data JPA (14개 엔티티)\n- Spring Security\n- PostgreSQL (운영), H2 (개발)\n- HikariCP 커넥션 풀\n\n### Frontend\n- Vanilla JavaScript SPA\n\n### Infrastructure\n- Docker\n- Koyeb/AWS\n\n## 구현 특징\n\n- STOMP over WebSocket 실시간 통신\n- /topic (그룹), /queue (1:1) 엔드포인트 분리\n- 14개 엔티티 간 관계 설계\n- 커넥션 풀링 및 배치 처리");
        beam.setDisplayOrder(2);

        // 3. TravelMate - Travel Companion Matching Platform
        ProjectEntity travelMate = new ProjectEntity();
        travelMate.setId("travelmate");
        travelMate.setTitle("TravelMate - 여행 동행자 매칭 플랫폼");
        travelMate.setDescription("같은 목적지로 떠나는 여행자를 연결하는 소셜 플랫폼. 위치 기반 매칭, 실시간 채팅, OAuth2 로그인 지원.");
        travelMate.setGitUrl("https://github.com/araeLaver/travle_mate.git");
        travelMate.setLiveUrl("https://various-belva-untab-1a59bee2.koyeb.app/");
        travelMate.setStack(Arrays.asList("Spring Boot 3.2", "Java 17", "React 18", "TypeScript", "PostgreSQL", "WebSocket", "Docker", "Nginx"));
        travelMate.setDetails("# TravelMate - 여행 동행자 매칭 플랫폼\n\n## 주요 기능\n\n### 동행자 매칭\n- 여행 그룹 생성 (목적지, 일정, 예산)\n- 유사한 여행 계획 사용자 추천\n- 위치 기반 동행자 찾기\n- 연령대, 성별, 관심사 필터링\n\n### 실시간 커뮤니케이션\n- WebSocket 채팅\n- 그룹 채팅방\n- 매칭 및 메시지 알림\n\n### 사용자 관리\n- 회원가입, 로그인, 프로필 관리\n- OAuth2 소셜 로그인\n- JWT 인증\n- 여행 스타일 및 관심사 프로필\n\n### 여행 정보\n- 목적지 추천\n- 지역별 가이드\n- 동행자/여행지 리뷰\n\n## 기술 스택\n\n### Backend\n- Spring Boot 3.2, Java 17\n- Spring Security + JWT\n- WebSocket\n- Spring Data JPA\n- PostgreSQL (운영), H2 (개발)\n\n### Frontend\n- React 18\n- TypeScript\n- Responsive CSS3\n- Axios\n- Create React App\n\n### Infrastructure\n- Docker + Docker Compose\n- Nginx\n- Supervisor\n- Windows Batch Scripts\n\n## 아키텍처\n\n- 모듈형 구조 (Backend, Frontend, Mobile 분리)\n- 컴포넌트 간 공유 DTO\n- RESTful API\n- Docker 컨테이너화\n- TypeScript 43.7%, Java 42.4%");
        travelMate.setDisplayOrder(3);

        // 4. Idea Manager - Creative Idea Management System
        ProjectEntity ideaManager = new ProjectEntity();
        ideaManager.setId("idea-manager");
        ideaManager.setTitle("Idea Manager - AI 기반 아이디어 관리 시스템");
        ideaManager.setDescription("창의적인 아이디어를 체계적으로 관리하고 AI가 자동으로 분류/태그를 제안하는 스마트 아이디어 관리 플랫폼입니다.");
        ideaManager.setGitUrl("https://github.com/araeLaver/idea_manager.git");
        ideaManager.setLiveUrl("https://ugliest-siana-untab-66b5b6a3.koyeb.app/");
        ideaManager.setStack(Arrays.asList("React 19", "TypeScript 5.8", "Express 5", "PostgreSQL 16", "Vite 7", "Recharts", "@dnd-kit", "JWT"));
        ideaManager.setDetails("# Idea Manager - AI 기반 아이디어 관리 시스템\n\n아이디어를 체계적으로 관리하고 AI가 자동으로 분류 및 태그를 제안하는 아이디어 관리 플랫폼입니다.\n\n## 주요 기능\n\n### 아이디어 관리\n- 아이디어 생성, 조회, 수정, 삭제\n- 상태 추적: Draft → In Progress → Completed → Archived\n- 우선순위 설정: High, Medium, Low\n- 카테고리 분류: 프로젝트, 비즈니스, 개인 등\n- 다중 태그 시스템\n\n### 칸반 보드\n- 드래그 앤 드롭 인터페이스\n- 4개 워크플로 컬럼\n- @dnd-kit 라이브러리 사용\n\n### 대시보드 및 통계\n- 상태별 통계 카드\n- Recharts 차트 시각화\n- 최근 활동 피드\n- 인기 카테고리 및 태그 분석\n\n### 데일리 메모\n- 날짜별 메모 관리\n- 대시보드 연동\n- 메모 검색 기능\n\n### AI 자동화\n- 자동 카테고리 분류\n- 태그 자동 추천\n- 아이디어 개선 제안\n\n### 히스토리 추적\n- 변경 로그 기록\n- 과거 버전 조회\n- 시간순 타임라인\n\n## 기술 스택\n\n### Frontend\n- React 19\n- TypeScript 5.8\n- Vite 7\n- React Router DOM 7\n- Recharts (차트)\n- @dnd-kit (드래그 앤 드롭)\n- Lucide React (아이콘)\n- date-fns (날짜 처리)\n\n### Backend\n- Express 5\n- PostgreSQL 16\n- JWT 인증\n- bcryptjs (비밀번호 암호화)\n\n### Database\n- Foreign Key 관계\n- Cascade Deletion\n- 인덱싱 최적화\n\n## 구현 특징\n\n- 반응형 디자인 (모바일, 태블릿, 데스크톱)\n- 다크모드 지원\n- 키보드 단축키 (Ctrl+N, Ctrl+K)\n- PWA 지원 (오프라인, 홈 화면 설치)\n- 계층 분리 아키텍처\n- RESTful API\n- TypeScript 타입 안전성\n- 코드 스플리팅 및 레이지 로딩");
        ideaManager.setDisplayOrder(4);

        projectRepository.save(devOrchestra);
        projectRepository.save(beam);
        projectRepository.save(travelMate);
        projectRepository.save(ideaManager);

        logger.info("Successfully initialized {} projects.", projectRepository.count());
    }

    private ProjectEntity createDevOrchestra() {
        ProjectEntity project = new ProjectEntity();
        project.setId("dev-orchestra");
        project.setTitle("DevOrchestra - AI 멀티 터미널 개발 보일러플레이트");
        project.setDescription("Claude Code 4개 터미널에 Architect/Implementor/Reviewer/DevOps 역할을 분리하여 AI와 협업하는 개발 템플릿. 파일 기반 비동기 통신으로 컨텍스트 리셋에도 상태가 보존됩니다.");
        project.setGitUrl("https://github.com/araeLaver/claude-code-multi-terminal.git");
        project.setLiveUrl(null);
        project.setStack(Arrays.asList("Claude Code", "Multi-Agent", "Shell Script", "Git Workflow", "Markdown", "tmux", "DevOps"));
        project.setDetails(
            "# DevOrchestra - AI 멀티 터미널 개발 보일러플레이트\n\n"
            + "Claude Code의 컨텍스트 윈도우 한계를 **설계적으로 해결**하는 멀티 터미널 개발 템플릿입니다.\n"
            + "하나의 터미널에서 모든 작업을 처리할 때 발생하는 역할 혼재, 컨텍스트 소진, 자기 리뷰 편향 문제를 \n"
            + "4개의 전문화된 터미널로 분리하여 해결합니다.\n\n"
            + "## 핵심 아이디어\n\n"
            + "### 단일 터미널의 문제\n"
            + "- 컨텍스트 윈도우 소진 → 초기 지시를 잊고 코드 품질 저하\n"
            + "- 역할 혼재 → \"자기 코드를 자기가 리뷰\"하는 편향 발생\n"
            + "- 역할 경계 붕괴 → 구현 중 설계 변경, 독립성 상실\n"
            + "- 병렬 작업 불가 → 순차적 처리만 가능\n\n"
            + "### 해결 방법\n"
            + "파일 시스템을 메시지 큐처럼 사용하는 **비동기 협업 체계**를 구축했습니다.\n"
            + "컨텍스트가 리셋되어도 `docs/` 디렉토리의 파일에서 즉시 상태를 복구할 수 있습니다.\n\n"
            + "## 4-Terminal 역할 체계\n\n"
            + "### T1 - Architect (두뇌)\n"
            + "- 프로젝트 전체 구조 설계 및 유지\n"
            + "- 모듈 간 인터페이스/계약 정의\n"
            + "- Task Spec 작성 (docs/tasks/TASK-XXX.md)\n"
            + "- 기술 의사결정 기록 (ADR)\n"
            + "- 다른 터미널의 질의 응답\n\n"
            + "### T2 - Implementor (손)\n"
            + "- PENDING 상태 Task를 실제 코드로 구현\n"
            + "- CLAUDE.md 코딩 컨벤션 엄격 준수\n"
            + "- 설계 의문 발생 시 questions.md에 기록\n"
            + "- 구현 완료 → Task 상태를 REVIEW로 변경\n\n"
            + "### T3 - Reviewer (눈)\n"
            + "- REVIEW 상태 Task의 코드 품질 검증\n"
            + "- 보안, 성능, 가독성, 컨벤션 관점 리뷰\n"
            + "- PASS / FAIL / CONDITIONAL_PASS 판정\n"
            + "- 테스트 코드 전담 작성\n\n"
            + "### T4 - DevOps (다리)\n"
            + "- Docker/CI/CD 파이프라인 구성\n"
            + "- 개발/스테이징/프로덕션 환경 관리\n"
            + "- 빌드, 린트, 테스트 자동화\n\n"
            + "## 파일 기반 통신 체계\n\n"
            + "각 터미널은 docs/ 디렉토리의 파일을 통해 비동기적으로 소통합니다.\n\n"
            + "- **docs/tasks/TASK-XXX.md** — Architect가 작성, Implementor가 구현\n"
            + "- **docs/reviews/REVIEW-XXX.md** — Reviewer가 작성, Implementor가 참조\n"
            + "- **docs/questions.md** — 모든 터미널이 질의, Architect가 답변\n"
            + "- **docs/decisions.md** — ADR 형식 기술 의사결정 기록\n\n"
            + "## Task 생명주기\n\n"
            + "PENDING → IN_PROGRESS → REVIEW → PASS → DONE\n\n"
            + "- FAIL 시 Implementor가 수정 후 REVIEW 재제출\n"
            + "- CONDITIONAL_PASS 시 경미한 수정 후 간략 재검증\n\n"
            + "## 컨텍스트 리셋 전략\n\n"
            + "Claude Code의 유한한 컨텍스트 윈도우를 관리하기 위해:\n\n"
            + "- Task 1개 완료 시 또는 대화 30턴 초과 시 리셋\n"
            + "- /clear → /project:역할명 으로 즉시 복구\n"
            + "- 모든 상태가 파일에 영속되므로 리셋 비용이 제로\n\n"
            + "## 자동화 도구\n\n"
            + "- **monitor.sh** — 실시간 상태 대시보드 (10초마다 갱신)\n"
            + "- **health-check.sh** — 프로젝트 구조 무결성 검증\n"
            + "- **new-task.sh** — Task 파일 자동 생성 (번호 자동 증가)\n"
            + "- **new-review.sh** — Review 파일 자동 생성\n\n"
            + "## Git 브랜치 전략\n\n"
            + "각 역할에 맞는 브랜치 접두사를 사용합니다:\n\n"
            + "- T1 Architect: docs/ (예: docs/architecture-v2)\n"
            + "- T2 Implementor: feat/ (예: feat/TASK-001-user-module)\n"
            + "- T3 Reviewer: test/ (예: test/TASK-001-user-tests)\n"
            + "- T4 DevOps: infra/ (예: infra/docker-setup)\n\n"
            + "## 이 템플릿의 가치\n\n"
            + "- AI의 컨텍스트 윈도우 제약을 **프로세스 설계로 극복**\n"
            + "- 팀 협업의 검증된 패턴을 **AI 환경에 최적화**\n"
            + "- 독립적 리뷰로 **자기 리뷰 편향 제거**\n"
            + "- 어떤 프로젝트든 즉시 적용 가능한 **보일러플레이트**\n"
            + "- 멀티 에이전트 AI 시대를 선제적으로 대응하는 **실전 솔루션**"
        );
        return project;
    }

    private void ensureDevOrchestraExists() {
        ProjectEntity fresh = createDevOrchestra();
        fresh.setDisplayOrder(1);
        projectRepository.findById("dev-orchestra").ifPresentOrElse(existing -> {
            if (!fresh.getTitle().equals(existing.getTitle())
                    || !fresh.getDescription().equals(existing.getDescription())
                    || !fresh.getDetails().equals(existing.getDetails())) {
                existing.setTitle(fresh.getTitle());
                existing.setDescription(fresh.getDescription());
                existing.setDetails(fresh.getDetails());
                existing.setStack(fresh.getStack());
                existing.setDisplayOrder(1);
                projectRepository.save(existing);
                logger.info("Updated DevOrchestra project data.");
            }
        }, () -> {
            projectRepository.save(fresh);
            logger.info("Added DevOrchestra project.");
        });
    }

    private void ensureDisplayOrder() {
        projectRepository.findById("dev-orchestra").ifPresent(p -> {
            if (p.getDisplayOrder() == null || p.getDisplayOrder() != 1) {
                p.setDisplayOrder(1);
                projectRepository.save(p);
            }
        });
        projectRepository.findById("beam-messenger").ifPresent(p -> {
            if (p.getDisplayOrder() == null || p.getDisplayOrder() != 2) {
                p.setDisplayOrder(2);
                projectRepository.save(p);
            }
        });
        projectRepository.findById("travelmate").ifPresent(p -> {
            if (p.getDisplayOrder() == null || p.getDisplayOrder() != 3) {
                p.setDisplayOrder(3);
                projectRepository.save(p);
            }
        });
        projectRepository.findById("idea-manager").ifPresent(p -> {
            if (p.getDisplayOrder() == null || p.getDisplayOrder() != 4) {
                p.setDisplayOrder(4);
                projectRepository.save(p);
            }
        });
    }

    private void updateProjectUrls() {
        projectRepository.findById("beam-messenger").ifPresent(beam -> {
            String newUrl = "https://chat-untab-fddd496d.koyeb.app/";
            if (!newUrl.equals(beam.getLiveUrl())) {
                beam.setLiveUrl(newUrl);
                projectRepository.save(beam);
                logger.info("Updated BEAM project liveUrl to: {}", newUrl);
            }
        });
    }
}
