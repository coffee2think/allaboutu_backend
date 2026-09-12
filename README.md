# 💄 AllAboutU

> **퍼스널컬러 진단과 스타일 추천, 커뮤니티 기능을 제공하는 뷰티·패션 플랫폼**

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?logo=springboot&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-DB-F80000?logo=oracle&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-Spring%20Data-6DB33F)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?logo=jsonwebtokens&logoColor=white)

> 🔗 Backend 레포 👉 [allaboutu_springboot](https://github.com/coffee2think/allaboutu_springboot)
> <br/> 🔗 Frontend 레포 👉 [allaboutu_vue](https://github.com/coffee2think/allaboutu_vue)

**AllAboutU**는 사용자의 얼굴 및 이미지 정보를 활용하여 퍼스널컬러와 체형을 분석하고,  
분석 결과를 바탕으로 코디와 스타일을 추천하는 뷰티·패션 서비스입니다.

스타일 추천에 그치지 않고 게시글, 댓글, 좋아요, 해시태그 등의 커뮤니티 기능과  
소셜 로그인, 얼굴 인식 로그인, AI 챗봇 등 다양한 기능을 하나의 서비스로 구성했습니다.

본 저장소는 6명이 함께 개발한 팀 프로젝트 **AllAboutU**를 개인 포트폴리오 관점에서 재정리한 저장소입니다.

---

## 📌 목차

1. [프로젝트 개요](#-프로젝트-개요)
2. [기술 스택](#-기술-스택)
3. [주요 기능](#-주요-기능)
4. [프로젝트 구조](#-프로젝트-구조)
5. [담당 역할](#-담당-역할)
6. [실행 환경](#-실행-환경)

---

## 📖 프로젝트 개요

| 구분 | 내용 |
|---|---|
| 프로젝트 | AllAboutU |
| 개발 기간 | 2023.10.30 ~ 2023.12.18 |
| 개발 인원 | 6명 |
| 형태 | 팀 프로젝트 |
| 담당 | Full Stack |
| Backend | Java 17, Spring Boot 3.2 |
| Frontend | Vue 3 |
| Database | Oracle |

### 프로젝트 핵심

AllAboutU는 **사용자 분석 → 개인화된 스타일 추천 → 커뮤니티 공유**로 이어지는 서비스를 목표로 개발했습니다.

주요 특징은 다음과 같습니다.

- 얼굴 이미지를 이용한 **퍼스널컬러 진단**
- 사용자 정보를 기반으로 한 **체형 및 스타일 분석**
- 분석 결과를 활용한 **코디·스타일 추천**
- ChatGPT API를 활용한 **스타일 추천 챗봇**
- Stable Diffusion을 활용한 **추천 스타일 이미지 생성**
- 일반 로그인과 **네이버 OAuth2 소셜 로그인**
- dlib 기반 **얼굴 인식 로그인**
- 게시글·댓글·좋아요·해시태그·파일 첨부를 지원하는 **커뮤니티**

### 담당 영역

6인 팀에서 부팀장 및 Full Stack 개발자로 참여했으며,
주로 커뮤니티 도메인과 네이버 OAuth2 로그인을 담당했습니다.

**Backend**
- 게시글 / 댓글 / 좋아요 / 해시태그 CRUD 및 연관관계 설계
- 다중 이미지 업로드
- 네이버 OAuth2 로그인
- 메일 기반 아이디·비밀번호 찾기

**Frontend**
- Vue 기반 커뮤니티 UI
- 게시글 목록 무한 스크롤
- 다중 이미지 업로드 UI

---

## 🔧 기술 스택

백엔드의 기본 구성뿐 아니라 주요 기능을 구현하는 과정에서 실제로 사용한 기술을 함께 정리했습니다.

| 영역 | 기술 도구 | 선정 및 활용 이유 |
|:---:|:---:|---|
| Backend | Java 17, Spring Boot 3.2 | REST API 기반으로 백엔드 서버를 구성하고 서비스의 비즈니스 로직을 구현하기 위해 사용했습니다. |
| DB | Oracle | 교육 과정에서 팀원들이 공통으로 사용해 온 RDBMS로, 팀 전체가 익숙한 개발 환경에서 협업하기 위해 선택했습니다. |
| ORM | Spring Data JPA | 엔티티 중심으로 데이터 모델을 구성하고 반복적인 CRUD 구현을 줄이며 객체 간 연관관계를 관리하기 위해 사용했습니다. |
| 인증 / 인가 | Spring Security, JWT | 사용자 인증과 API 접근 권한을 관리하고 Access Token과 Refresh Token을 이용한 토큰 기반 인증을 구현했습니다. |
| 소셜 로그인 | Naver OAuth2 | 네이버 계정을 이용한 간편 로그인을 제공하기 위해 OAuth2 인증과 Access Token 발급, 회원 정보 조회 API를 연동했습니다. |
| 메일 인증 | Spring Mail, JavaMailSender | 아이디·비밀번호 찾기 과정에서 인증번호를 생성하여 이메일로 발송하고 검증하기 위해 사용했습니다. |
| AI 챗봇 | ChatGPT API | 사용자의 질문과 요구사항을 바탕으로 코디와 스타일을 추천하는 대화형 기능을 구현했습니다. |
| AI 이미지 생성 | Gradio, Stable Diffusion | 챗봇에서 생성된 스타일 추천 결과를 이미지 형태로 제공하기 위해 이미지 생성 모델과 연동했습니다. |
| 얼굴 인식 | Python, dlib | 사용자 얼굴 특징을 추출하고 등록된 얼굴 정보와 비교하여 얼굴 인식 로그인을 구현했습니다. |

> 퍼스널컬러 및 체형 분석 기능은 별도의 이미지 분석 로직과 연계되어 있으며, 백엔드에서는 분석 요청과 결과 데이터를 서비스 기능과 연결합니다.

---

## ✨ 주요 기능

### 👤 회원 및 인증

- 일반 회원가입 및 로그인
- Spring Security 기반 인증·인가
- JWT Access Token / Refresh Token 관리
- 네이버 OAuth2 소셜 로그인
- 이메일 인증
- 아이디·비밀번호 찾기
- 얼굴 이미지 기반 얼굴 인식 로그인

### 🎨 퍼스널컬러 및 사용자 분석

- 사용자 이미지 기반 퍼스널컬러 진단
- 분석 결과 저장 및 조회
- 사용자 분석 결과를 코디·스타일 추천 기능과 연결
- 체형 분석 결과를 활용한 개인화 기능 제공

### 👗 코디 및 스타일 추천

- 퍼스널컬러 및 사용자 분석 결과 기반 스타일 추천
- 코디 및 스타일 관련 정보 조회
- 사용자에게 적합한 패션·스타일 정보 제공

### 🤖 OOTD

- ChatGPT API와 연동한 스타일 상담
- 사용자의 입력을 기반으로 코디 및 스타일 추천
- 추천 결과를 이미지 생성 기능과 연계

### 🖼 AI 이미지 생성

- AI 챗봇의 추천 결과를 이미지 생성 입력값으로 활용
- Gradio를 통해 이미지 생성 모델과 연동
- Stable Diffusion 기반 스타일 이미지 생성

### 💬 커뮤니티

- 게시글 등록·조회·수정·삭제
- 댓글 작성 및 관리
- 게시글 좋아요
- 해시태그 등록 및 검색
- 다중 파일 첨부
- 게시글 목록 조회

### 🧑 마이페이지

- 회원 프로필 조회 및 수정
- 사용자 활동 내역 관리
- 작성 게시글 등 사용자별 데이터 조회

### 📢 공지 및 관리자

- 공지사항 등록·조회
- 회원 관리
- 신고 데이터 관리

---

## 📂 프로젝트 구조

백엔드는 기능 영역을 기준으로 패키지를 분리하고, 각 영역 내부에서 Controller / Service / Repository / Domain 계층을 구성했습니다.

```text
org.ict.allaboutu
├── Access          # 접근 권한 관련 기능
├── admin           # 관리자 / 회원·신고 관리
├── board           # 게시글 / 댓글 / 좋아요 / 해시태그 / 첨부파일
├── cody            # 코디 추천
├── common          # 공통 유틸리티
├── config          # Spring Security / JWT 설정
├── face            # 얼굴 인식
├── member          # 회원 관리 / 인증
├── myPage          # 마이페이지
├── notice          # 공지사항
├── oauth           # OAuth 인증
├── personalcolor   # 퍼스널컬러 진단
├── style           # 스타일 정보
└── website         # 추천 사이트 정보
```

주요 도메인은 다음과 같이 계층을 분리했습니다.

```text
domain
├── controller      # HTTP 요청/응답 처리
├── service         # 비즈니스 로직
├── repository      # 데이터 접근
└── domain          # Entity / DTO 등 도메인 객체
```

---

## 👨‍💻 담당 역할

팀 프로젝트에서 **Full Stack 개발 및 부팀장**으로 참여했습니다.

### Backend

#### 커뮤니티 기능

- 게시글 및 댓글 기능 구현
- 게시글 좋아요 기능 구현
- 해시태그 등록 및 검색 기능 구현
- 게시글과 첨부파일 데이터 관계 처리
- 다중 파일 업로드 기능 구현

#### 네이버 소셜 로그인

- 네이버 OAuth2 인증 흐름 구현
- Authorization Code를 이용한 Access Token 요청
- Access Token을 이용한 네이버 회원 정보 조회
- 조회한 사용자 정보와 서비스 회원 정보를 연결하여 로그인 처리

#### 메일 기반 아이디·비밀번호 찾기

- JavaMailSender

### Frontend

- Vue 기반 커뮤니티 화면 구현
- 게시글 목록 및 상세 화면 연동
- 게시글 작성·수정 화면 구현
- 댓글·좋아요 등 커뮤니티 기능과 REST API 연동
- 게시글 목록 무한 스크롤 구현
- 다중 이미지 업로드 UI와 백엔드 API 연동

### 협업

- Feature Branch 기반 Git 협업
- 팀원 작업 내용 및 진행 상황 공유
- REST API를 기준으로 프론트엔드와 백엔드 기능 연동
- 데이터 구조와 API 요청·응답 형식을 맞추며 기능 통합

---

## 🔍 주요 구현 경험

### 다중 파일 업로드

게시글에 여러 이미지를 첨부할 수 있도록 게시글과 첨부파일 데이터를 분리하여 관리했습니다.

단순히 파일을 저장하는 것에 그치지 않고,

```text
게시글 생성
    ↓
게시글 ID 생성
    ↓
복수 파일 저장
    ↓
첨부파일 정보와 게시글 ID 연결
```

과 같이 **게시글 데이터와 파일 데이터의 관계 및 처리 순서**를 고려하여 구현했습니다.

이 과정에서 파일 처리 실패 시 게시글 데이터와 첨부파일 데이터의 상태가 어긋날 수 있는 문제를 경험했고, 기능 구현 시 정상 흐름뿐 아니라 실패 상황에서의 데이터 정합성도 함께 고려해야 한다는 점을 학습했습니다.

### 네이버 OAuth2 로그인

네이버 로그인은 단순 SDK 호출이 아니라 OAuth2 인증 흐름을 직접 연결했습니다.

```text
사용자
  ↓
네이버 인증
  ↓
Authorization Code
  ↓
Access Token 발급
  ↓
네이버 사용자 정보 조회
  ↓
서비스 회원 확인
  ↓
로그인
```

외부 인증 서비스를 백엔드 회원 시스템과 연결하면서 **외부 API 응답과 내부 회원 데이터 사이의 변환 및 인증 흐름**을 경험했습니다.

---

## 🚀 실행 환경

### Backend

- Java 17
- Spring Boot 3.2
- Oracle Database

```bash
git clone https://github.com/coffee2think/allaboutu_springboot.git
cd allaboutu_springboot
./gradlew bootRun
```

> Oracle 연결 정보, JWT Secret, OAuth Client 정보, Mail Server 설정 등 외부 환경 설정이 필요합니다.

### Frontend

프론트엔드 프로젝트는 별도의 저장소에서 관리합니다.

```bash
git clone https://github.com/coffee2think/allaboutu_vue.git
cd allaboutu_vue
npm install
npm run serve
```

---

## 📎 Repository

- **Backend**  
  https://github.com/coffee2think/allaboutu_springboot

- **Frontend**  
  https://github.com/coffee2think/allaboutu_vue
