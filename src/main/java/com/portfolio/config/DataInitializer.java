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
        logger.info("Initializing database with featured portfolio projects...");
        projectRepository.deleteAll();
        logger.info("Cleared existing data for fresh initialization.");

        // 1. BEAM - Global Security Messenger
        ProjectEntity beam = new ProjectEntity();
        beam.setId("beam-messenger");
        beam.setTitle("BEAM - 글로벌 보안 메신저");
        beam.setDescription("WebSocket 기반 실시간 메신저. 1:1 채팅, 그룹 채팅(100명), 파일 공유, JWT 인증, End-to-End 암호화 지원.");
        beam.setGitUrl("https://github.com/araeLaver/simple-chat-server");
        beam.setLiveUrl("https://homeless-elyn-untab-5dcfc664.koyeb.app/");
        beam.setStack(Arrays.asList("Java 17", "Spring Boot 3.2", "WebSocket (STOMP)", "Spring Security", "PostgreSQL", "Docker", "Koyeb"));
        beam.setDetails("# BEAM - Global Security Messenger\n\n## 주요 기능\n\n### 실시간 메시징\n- 1:1 다이렉트 메시지\n- 그룹 채팅 (최대 100명)\n- 타이핑 인디케이터\n- 온라인 상태 표시\n- 읽음 확인\n- 메시지 검색\n\n### 소셜\n- 친구 요청/수락/거절/차단\n- 친구 목록 관리\n- 프로필 및 상태 메시지\n\n### 파일 공유\n- 최대 10MB 파일 업로드\n- 이미지/비디오 썸네일 자동 생성\n- 다양한 파일 형식 지원\n- 다운로드 내역 추적\n\n### 보안\n- JWT 토큰 인증\n- BCrypt 비밀번호 암호화\n- End-to-End 암호화 프레임워크\n- 전화번호 인증\n\n## 기술 스택\n\n### Backend\n- Java 17, Spring Boot 3.2\n- WebSocket (STOMP + SockJS)\n- Spring Data JPA (14개 엔티티)\n- Spring Security\n- PostgreSQL (운영), H2 (개발)\n- HikariCP 커넥션 풀\n\n### Frontend\n- Vanilla JavaScript SPA\n\n### Infrastructure\n- Docker\n- Koyeb/AWS\n\n## 구현 특징\n\n- STOMP over WebSocket 실시간 통신\n- /topic (그룹), /queue (1:1) 엔드포인트 분리\n- 14개 엔티티 간 관계 설계\n- 커넥션 풀링 및 배치 처리");

        // 2. TravelMate - Travel Companion Matching Platform
        ProjectEntity travelMate = new ProjectEntity();
        travelMate.setId("travelmate");
        travelMate.setTitle("TravelMate - 여행 동행자 매칭 플랫폼");
        travelMate.setDescription("같은 목적지로 떠나는 여행자를 연결하는 소셜 플랫폼. 위치 기반 매칭, 실시간 채팅, OAuth2 로그인 지원.");
        travelMate.setGitUrl("https://github.com/araeLaver/TravelMate");
        travelMate.setLiveUrl("https://breakable-barbee-untab-34f4435e.koyeb.app/");
        travelMate.setStack(Arrays.asList("Spring Boot 3.2", "Java 17", "React 18", "TypeScript", "PostgreSQL", "WebSocket", "Docker", "Nginx"));
        travelMate.setDetails("# TravelMate - 여행 동행자 매칭 플랫폼\n\n## 주요 기능\n\n### 동행자 매칭\n- 여행 그룹 생성 (목적지, 일정, 예산)\n- 유사한 여행 계획 사용자 추천\n- 위치 기반 동행자 찾기\n- 연령대, 성별, 관심사 필터링\n\n### 실시간 커뮤니케이션\n- WebSocket 채팅\n- 그룹 채팅방\n- 매칭 및 메시지 알림\n\n### 사용자 관리\n- 회원가입, 로그인, 프로필 관리\n- OAuth2 소셜 로그인\n- JWT 인증\n- 여행 스타일 및 관심사 프로필\n\n### 여행 정보\n- 목적지 추천\n- 지역별 가이드\n- 동행자/여행지 리뷰\n\n## 기술 스택\n\n### Backend\n- Spring Boot 3.2, Java 17\n- Spring Security + JWT\n- WebSocket\n- Spring Data JPA\n- PostgreSQL (운영), H2 (개발)\n\n### Frontend\n- React 18\n- TypeScript\n- Responsive CSS3\n- Axios\n- Create React App\n\n### Infrastructure\n- Docker + Docker Compose\n- Nginx\n- Supervisor\n- Windows Batch Scripts\n\n## 아키텍처\n\n- 모듈형 구조 (Backend, Frontend, Mobile 분리)\n- 컴포넌트 간 공유 DTO\n- RESTful API\n- Docker 컨테이너화\n- TypeScript 43.7%, Java 42.4%");

        // 3. Idea Manager - Creative Idea Management System
        ProjectEntity ideaManager = new ProjectEntity();
        ideaManager.setId("idea-manager");
        ideaManager.setTitle("Idea Manager - AI 기반 아이디어 관리 시스템");
        ideaManager.setDescription("창의적인 아이디어를 체계적으로 관리하고 AI가 자동으로 분류/태그를 제안하는 스마트 아이디어 관리 플랫폼입니다.");
        ideaManager.setGitUrl("https://github.com/araeLaver/idea-manager");
        ideaManager.setLiveUrl("https://idea-manager-two.vercel.app");
        ideaManager.setStack(Arrays.asList("React 19", "TypeScript 5.8", "Express 5", "PostgreSQL 16", "Vite 7", "Recharts", "@dnd-kit", "JWT"));
        ideaManager.setDetails("# Idea Manager - AI 기반 아이디어 관리 시스템\n\n아이디어를 체계적으로 관리하고 AI가 자동으로 분류 및 태그를 제안하는 아이디어 관리 플랫폼입니다.\n\n## 주요 기능\n\n### 아이디어 관리\n- 아이디어 생성, 조회, 수정, 삭제\n- 상태 추적: Draft → In Progress → Completed → Archived\n- 우선순위 설정: High, Medium, Low\n- 카테고리 분류: 프로젝트, 비즈니스, 개인 등\n- 다중 태그 시스템\n\n### 칸반 보드\n- 드래그 앤 드롭 인터페이스\n- 4개 워크플로 컬럼\n- @dnd-kit 라이브러리 사용\n\n### 대시보드 및 통계\n- 상태별 통계 카드\n- Recharts 차트 시각화\n- 최근 활동 피드\n- 인기 카테고리 및 태그 분석\n\n### 데일리 메모\n- 날짜별 메모 관리\n- 대시보드 연동\n- 메모 검색 기능\n\n### AI 자동화\n- 자동 카테고리 분류\n- 태그 자동 추천\n- 아이디어 개선 제안\n\n### 히스토리 추적\n- 변경 로그 기록\n- 과거 버전 조회\n- 시간순 타임라인\n\n## 기술 스택\n\n### Frontend\n- React 19\n- TypeScript 5.8\n- Vite 7\n- React Router DOM 7\n- Recharts (차트)\n- @dnd-kit (드래그 앤 드롭)\n- Lucide React (아이콘)\n- date-fns (날짜 처리)\n\n### Backend\n- Express 5\n- PostgreSQL 16\n- JWT 인증\n- bcryptjs (비밀번호 암호화)\n\n### Database\n- Foreign Key 관계\n- Cascade Deletion\n- 인덱싱 최적화\n\n## 구현 특징\n\n- 반응형 디자인 (모바일, 태블릿, 데스크톱)\n- 다크모드 지원\n- 키보드 단축키 (Ctrl+N, Ctrl+K)\n- PWA 지원 (오프라인, 홈 화면 설치)\n- 계층 분리 아키텍처\n- RESTful API\n- TypeScript 타입 안전성\n- 코드 스플리팅 및 레이지 로딩");

        projectRepository.save(beam);
        projectRepository.save(travelMate);
        projectRepository.save(ideaManager);

        logger.info("Successfully initialized {} projects.", projectRepository.count());
    }
}