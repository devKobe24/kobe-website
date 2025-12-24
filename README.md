Kobe-website README.md
---

# 🎨 Kobe Website

> 개인 포트폴리오 및 프로젝트 관리를 위한 웹 애플리케이션

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.12-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-17-blue.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

## 📖 프로젝트 소개

**Kobe Website**는 개인 포트폴리오와 프로젝트를 효과적으로 관리하고 전시할 수 있는 웹 애플리케이션입니다.  
Spring Boot 기반으로 구축되었으며, 관리자 페이지를 통해 쉽게 프로젝트를 등록하고 관리할 수 있습니다.

### ✨ 주요 특징

- 🔐 **보안 인증 시스템**: Spring Security 기반의 관리자 인증
- 📝 **Markdown 지원**: 프로젝트 설명 작성 시 Markdown 문법 활용 가능
- 🖼️ **이미지 최적화**: 자동 리사이징 및 AWS S3 저장으로 빠른 로딩 속도 제공
- 🎯 **반응형 디자인**: Bootstrap 5를 활용한 모바일 친화적 UI
- 🌐 **환경별 설정**: Dev/Prod 프로필을 통한 유연한 환경 관리

---

## 🚀 주요 기능

### 👤 사용자 기능
- ✅ 프로젝트 포트폴리오 조회
- ✅ 프로젝트 상세 정보 확인
- ✅ 외부 링크(GitHub, Demo) 연결

### 🛠️ 관리자 기능
- ✅ 프로젝트 등록/삭제
- ✅ 이미지 업로드 (자동 리사이징)
- ✅ Markdown 기반 프로젝트 설명 작성
- ✅ 실시간 Markdown 미리보기
- ✅ 안전한 로그인/로그아웃

---

## 🛠️ 기술 스택

### Backend
- **Framework**: Spring Boot 3.4.12
- **Language**: Java 17
- **Security**: Spring Security 6
- **Database**: H2 (Dev) / MySQL (Prod)
- **ORM**: Spring Data JPA

### Cloud & Storage
- **AWS S3**: 이미지 저장소
- **AWS Secrets Manager**: 민감 정보 관리

### Frontend
- **Template Engine**: Thymeleaf
- **UI Framework**: Bootstrap 5
- **Markdown Parser**: Marked.js

### Utilities
- **Image Processing**: Thumbnailator (이미지 리사이징)
- **Markdown Parsing**: CommonMark
- **Build Tool**: Gradle 9.2.1

---

## 📦 시작하기

### 사전 요구사항

- ☕ Java 17 이상
- 🐘 Gradle 9.x
- 🗄️ MySQL 8.x (운영 환경)
- ☁️ AWS 계정 (S3 및 Secrets Manager 사용 시)

### 설치 및 실행

#### 1️⃣ 프로젝트 클론
```bash
git clone https://github.com/yourusername/kobe-website.git
cd kobe-website
```

#### 2️⃣ 환경 설정 파일 생성
```bash
# src/main/resources/application-dev.yml 생성
cp src/main/resources/application.yml src/main/resources/application-dev.yml
```

**application-dev.yml 예시**:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: create

app:
  admin:
    username: admin
    password: your-password-here
```

#### 3️⃣ 빌드 및 실행
```bash
# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행 (개발 환경)
./gradlew bootRun

# 또는 JAR 파일 실행
java -jar build/libs/kobe-website-0.0.4.jar
```

#### 4️⃣ 접속
- 🌐 메인 페이지: http://localhost:8080
- 🔑 관리자 페이지: http://localhost:8080/admin/dashboard
- 🗄️ H2 Console (dev): http://localhost:8080/h2-console
- ✅ 운영 페이지: https://min-seong.com/

---

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/kobe/website/
│   │   ├── config/              # 보안 및 초기 데이터 설정
│   │   │   ├── DevSecurityConfig.java
│   │   │   ├── ProdSecurityConfig.java
│   │   │   └── InitData.java
│   │   ├── controller/          # 웹 요청 처리
│   │   │   ├── AdminController.java
│   │   │   ├── MainController.java
│   │   │   ├── ProjectController.java
│   │   │   └── ImageController.java
│   │   ├── domain/              # 엔티티 정의
│   │   │   ├── Member.java
│   │   │   └── Project.java
│   │   ├── dto/                 # 데이터 전송 객체
│   │   │   └── ProjectFormDto.java
│   │   ├── repository/          # 데이터 접근 계층
│   │   │   ├── MemberRepository.java
│   │   │   └── ProjectRepository.java
│   │   ├── service/             # 비즈니스 로직
│   │   │   ├── ProjectService.java
│   │   │   └── CustomUserDetailsService.java
│   │   └── util/                # 유틸리티 클래스
│   │       ├── ImageResizeUtil.java
│   │       └── MarkdownUtil.java
│   └── resources/
│       ├── application.yml      # 공통 설정
│       ├── application-dev.yml  # 개발 환경 설정
│       ├── application-prod.yml # 운영 환경 설정
│       └── templates/           # Thymeleaf 템플릿
│           ├── index.html
│           ├── login.html
│           └── admin/
│               ├── dashboard.html
│               ├── project-form.html
│               └── project-list.html
└── test/
    └── java/com/kobe/website/
```

---

## ⚙️ 설정 가이드

### 🔐 AWS 설정

#### S3 Bucket 생성
1. AWS Console에서 S3 버킷 생성 (`kobe-website-uploads`)
2. 버킷 정책 설정 (EC2 인스턴스 프로파일 권한 부여)

#### Secrets Manager (선택사항)
```yaml
spring:
  cloud:
    aws:
      secretsmanager:
        enabled: true
```

### 🗄️ 데이터베이스 설정

#### 개발 환경 (H2)
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  h2:
    console:
      enabled: true
```

#### 운영 환경 (MySQL)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/kobe_website
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
```

---

## 🌐 API 엔드포인트

### 공개 페이지
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | 메인 페이지 (프로젝트 목록) |
| GET | `/login` | 로그인 페이지 |
| GET | `/uploads/{filename}` | 이미지 조회 (S3) |

### 관리자 페이지 (인증 필요)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/dashboard` | 관리자 대시보드 |
| GET | `/admin/projects` | 프로젝트 관리 목록 |
| GET | `/admin/projects/new` | 프로젝트 등록 폼 |
| POST | `/admin/projects/new` | 프로젝트 등록 처리 |
| POST | `/admin/projects/{id}/delete` | 프로젝트 삭제 |
| POST | `/logout` | 로그아웃 |

---

## 🎯 주요 기능 상세

### 1️⃣ 이미지 자동 리사이징
- 업로드된 이미지를 **1200x675 (16:9)** 비율로 자동 리사이징
- JPEG 포맷으로 통일하여 용량 최적화 (품질 85%)
- S3에 자동 업로드 및 관리

### 2️⃣ Markdown 지원
```markdown
# 제목
- **굵게**
- *기울임*
- `코드`
- [링크](https://example.com)
```
실시간 미리보기 기능으로 작성 중 바로 확인 가능!

### 3️⃣ 환경별 보안 설정

**개발 환경 (DevSecurityConfig)**:
- H2 Console 접근 허용
- CSRF 예외 처리
- 디버깅 용이한 설정

**운영 환경 (ProdSecurityConfig)**:
- 엄격한 인증 요구
- CSRF 보호 활성화
- GET 요청만 공개 허용

---

## 🏗️ 빌드 및 배포

### JAR 빌드
```bash
./gradlew clean bootJar
```
빌드 결과: `build/libs/kobe-website-0.0.4.jar`

### 운영 환경 실행
```bash
java -jar \
  -Dspring.profiles.active=prod \
  -Dserver.port=8080 \
  build/libs/kobe-website-0.0.4.jar
```

### Docker 배포 (선택사항)
```dockerfile
FROM eclipse-temurin:17-jre
COPY build/libs/kobe-website-0.0.4.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

---

## 📝 라이센스

이 프로젝트는 MIT 라이센스 하에 배포됩니다.

---

## 📧 연락처

**MIN SEONG KANG (KOBE)**

- 📧 Email: dev.skyachieve91@gmail.com
- 💼 GitHub: [@devKobe24](https://github.com/devKobe24)

---

## 🙏 Ref.

이 프로젝트는 다음 오픈소스 라이브러리를 활용했습니다:
- Spring Boot & Spring Security
- Thymeleaf
- Bootstrap
- Thumbnailator
- CommonMark
- AWS SDK