**진행중**
<br><br><br>

**서비스플로우** 
- QR 기반 스캔 처리 → 수량 변화 감지 → 추적 로그 저장
```aiignore
  1) 작업자가 출고 현장에서 QR코드를 스캔
  2) 스캔된 QR코드는 orderItemId 혹은 productId와 매핑됨
  3) 스캔 수량이 누적되면서 출고 수량이 증가
  4) ShipmentLog에 스캔 내역이 저장됨 
  5) 출고 수량이 주문 수량과 일치하면 상태를 “완료”로 변경
```

<br>
<br>

**프로젝트 구조**
- msa적용을 고려한 구조
- api: 실행파일
- infra : Docker, DB설정
- health : 헬스체크용
- domain : 도메인



```
.
├── Dockerfile
├── api
│   ├── build
│   │   ├── classes
│   │   │   └── java
│   │   │       ├── main
│   │   │       │   ├── com
│   │   │       │   │   └── qrlogi
│   │   │       │   │       └── api
│   │   │       │   │           ├── QrLogiApplication.class
│   │   │       │   │           ├── config
│   │   │       │   │           │   ├── DataSrcConfig.class
│   │   │       │   │           │   ├── JwtTokenFilter.class
│   │   │       │   │           │   ├── JwtTokenProvider.class
│   │   │       │   │           │   ├── SecurityConfig.class
│   │   │       │   │           │   └── SwaggerConfig.class
│   │   │       │   │           ├── controller
│   │   │       │   │           │   ├── BuyerController.class
│   │   │       │   │           │   ├── OrderController.class
│   │   │       │   │           │   ├── OrderItemController.class
│   │   │       │   │           │   ├── PaymentController.class
│   │   │       │   │           │   ├── ProductController.class
│   │   │       │   │           │   ├── QrScanlogController.class
│   │   │       │   │           │   ├── ShipmentController.class
│   │   │       │   │           │   ├── ShipmentItemController.class
│   │   │       │   │           │   └── UserController.class
│   │   │       │   │           └── health
│   │   │       │   │               └── HealthCheckController.class
│   │   │       │   └── flgfy
│   │   │       │       └── qrlogi
│   │   │       │           └── common
│   │   │       │               ├── dto
│   │   │       │               │   ├── CommonResponse.class
│   │   │       │               │   ├── ErrorInfo$ErrorInfoBuilder.class
│   │   │       │               │   └── ErrorInfo.class
│   │   │       │               ├── enums
│   │   │       │               │   └── ReturnCode.class
│   │   │       │               ├── exception
│   │   │       │               │   ├── BusinessException.class
│   │   │       │               │   └── GlobalExceptionHandler.class
│   │   │       │               └── util
│   │   │       │                   └── DateTimeUtil.class
│   │   │       └── test
│   │   │           └── com
│   │   │               └── qrlogi
│   │   │                   └── api
│   │   │                       └── QrLogiApplicationTests.class
│   │   ├── libs
│   │   ├── resources
│   │   │   └── main
│   │   │       ├── application-docker.yml
│   │   │       ├── application.yml
│   │   │       ├── sql
│   │   │       │   └── erd.sql
│   │   │       ├── static
│   │   │       │   └── favicon.ico
│   │   │       └── templates
│   ├── build.gradle
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── qrlogi
│       │   │           └── api
│       │   │               ├── QrLogiApplication.java
│       │   │               ├── common
│       │   │               │   ├── dto
│       │   │               │   │   ├── CommonResponse.java
│       │   │               │   │   └── ErrorInfo.java
│       │   │               │   ├── enums
│       │   │               │   │   └── ReturnCode.java
│       │   │               │   ├── exception
│       │   │               │   │   ├── BusinessException.java
│       │   │               │   │   └── GlobalExceptionHandler.java
│       │   │               │   └── util
│       │   │               │       └── DateTimeUtil.java
│       │   │               ├── config
│       │   │               │   ├── DataSrcConfig.java
│       │   │               │   ├── JwtTokenFilter.java
│       │   │               │   ├── JwtTokenProvider.java
│       │   │               │   ├── SecurityConfig.java
│       │   │               │   └── SwaggerConfig.java
│       │   │               ├── controller
│       │   │               │   ├── BuyerController.java
│       │   │               │   ├── OrderController.java
│       │   │               │   ├── OrderItemController.java
│       │   │               │   ├── PaymentController.java
│       │   │               │   ├── ProductController.java
│       │   │               │   ├── QrScanlogController.java
│       │   │               │   ├── ShipmentController.java
│       │   │               │   ├── ShipmentItemController.java
│       │   │               │   └── UserController.java
│       │   │               └── health
│       │   │                   └── HealthCheckController.java
│       │   └── resources
│       │       ├── application-docker.yml
│       │       ├── application.yml
│       │       ├── sql
│       │       │   └── erd.sql
│       │       ├── static
│       │       │   └── favicon.ico
│       │       └── templates
│       └── test
│           └── java
│               └── com
│                   └── qrlogi
│                       └── api
│                           └── QrLogiApplicationTests.java
├── domain
│   ├── build.gradle
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── qrlogi
│           │           └── domain
│           │               ├── buyer
│           │               │   ├── dto
│           │               │   │   ├── BuyerRequest.java
│           │               │   │   └── BuyerResponse.java
│           │               │   ├── entity
│           │               │   │   └── Buyer.java
│           │               │   ├── repository
│           │               │   │   └── BuyerRepository.java
│           │               │   └── service
│           │               │       └── BuyerService.java
│           │               ├── order
│           │               │   ├── dto
│           │               │   │   ├── OrderRequest.java
│           │               │   │   └── OrderResponse.java
│           │               │   ├── entity
│           │               │   │   ├── OrderStatus.java
│           │               │   │   └── Orders.java
│           │               │   ├── repository
│           │               │   └── service
│           │               │       └── OrderService.java
│           │               ├── orderitem
│           │               │   └── orderitem
│           │               │       ├── dto
│           │               │       │   ├── OrderItemDTO.java
│           │               │       │   └── OrderItemRequest.java
│           │               │       ├── entity
│           │               │       │   └── OrderItem.java
│           │               │       ├── repository
│           │               │       └── service
│           │               ├── payment
│           │               │   ├── dto
│           │               │   ├── entity
│           │               │   │   ├── Payment.java
│           │               │   │   ├── PaymentMethod.java
│           │               │   │   └── PaymentStatus.java
│           │               │   ├── repository
│           │               │   └── service
│           │               ├── product
│           │               │   ├── dto
│           │               │   ├── entity
│           │               │   │   └── Product.java
│           │               │   ├── repository
│           │               │   └── service
│           │               ├── qrscanlog
│           │               │   ├── dto
│           │               │   ├── entity
│           │               │   │   ├── QrScanLog.java
│           │               │   │   └── ScanStatus.java
│           │               │   ├── repository
│           │               │   └── service
│           │               ├── shipment
│           │               │   ├── dto
│           │               │   ├── entity
│           │               │   │   └── Shipment.java
│           │               │   ├── repository
│           │               │   └── service
│           │               ├── shipmentitem
│           │               │   ├── dto
│           │               │   ├── entity
│           │               │   │   └── ShipmentItem.java
│           │               │   ├── repository
│           │               │   └── service
│           │               └── user
│           │                   ├── dto
│           │                   │   ├── DeleteRequest.java
│           │                   │   ├── LoginRequest.java
│           │                   │   ├── LoginResponse.java
│           │                   │   ├── SignRequest.java
│           │                   │   └── SignResponse.java
│           │                   ├── entity
│           │                   │   ├── User.java
│           │                   │   ├── UserPrincipal.java
│           │                   │   └── UserRole.java
│           │                   ├── repository
│           │                   │   └── UserRepository.java
│           │                   └── service
│           │                       ├── AuthService.java
│           │                       └── LoginUserDetailService.java
│           └── resources
├── infra
│   └── docker-compose.yml
└── settings.gradle


```
<br><br><br>
**Docker**
- MySql
- Master-Slave구조 (springApp, master, slave)

<br><br>
**Snowflake**
- orderitem일련번호에 적용

<br><br>

```
└── domain
    ├── buyer              : 구매자 정보 관리
    ├── order              : 주문 생성 및 조회
    ├── orderitem          : 주문 항목 단위 관리
    ├── product            : 제품 관리
    ├── shipment           : 출고 관리 
    ├── shipmentitem       : 출고 항목 
    ├── inspection         : 출고 검수
    ├── payment            : 결제 관리 (예정)
    └── user               : 사용자 인증 및 관리
```
