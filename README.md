## 프로젝트 소개
회원, 상품, 주문, 결제 등을 구현하며 커머스 도메인의 핵심 비즈니스 로직을 다룹니다.

헥사고날 아키텍처와 멀티모듈 구조를 통해 도메인 중심 설계와 관심사의 분리를 실현하였으며, 

모듈간 필요한 부분만 서로 의존하도록 구성하여 확장성과 유지보수성을 높입니다.

## 기술 스택
- Kotlin
- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- JUnit5 + Mockk
- RestDocs + Swagger
- H2 Database (-> MySQL)
- Redis (TBD)
- Docker Compose (TBD)
- Testcontainers (TBD)
- Message Queue (TBD)

## 프로젝트 아키텍처
`api`, `app`, `domain` 모듈은 모두 컴파일 타임에 `infra`에 대한 의존성을 가지지 않으며, `infra`는 `api`의 **런타임 시점**에만 주입됩니다.

다양한 외부 시스템 연동과 변경에 유연하게 대응할 수 있도록 헥사고날 아키텍처를 적용하여 인프라에 대한 의존성을 최소화 했습니다.
```
- API Layer (core-api)
    - API 엔드포인트 제공
    - 요청/응답 DTO 정의
    - 인증/인가 구현
    - HTTP 응답 변환, 에러 코드
- Application Layer (core-app)
    - 유스케이스 구현
    - DTO ↔ 도메인 모델 변환 및 입력 검증
- Domain Layer ( core-domain)
    - 도메인 모델 정의
    - 외부 의존성 없는 핵심 비즈니스 규칙 정의
- Infrastructure Layer (core-infra)
    - Repository 구현
    - 외부 연계
- common Layer (core-common)
    - 확장함수 구현
    - 공통 유틸리티 기능
```
모듈간 의존성 방향은 도메인 중심으로 구성됩니다.
```
api -> application -> domain <- infrastructure
```