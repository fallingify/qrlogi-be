### 진행 중
<br><br><br>

---


**프로젝트 구조**

- 도메인별 멀티모듈
- api: spring application 실행
- infra : Docker, DB설정
- health : 헬스체크
- domain - : 도메인별 독립
```

/
├── Dockerfile
├── HELP.md
├── README.md
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
│   │   ├── generated
│   │   │   └── sources
│   │   │       ├── annotationProcessor
│   │   │       │   └── java
│   │   │       │       ├── main
│   │   │       │       └── test
│   │   │       └── headers
│   │   │           └── java
│   │   │               ├── main
│   │   │               └── test
│   │   ├── libs
│   │   │   └── api-0.0.1-SNAPSHOT.jar
│   │   ├── reports
│   │   │   └── tests
│   │   │       └── test
│   │   │           ├── classes
│   │   │           │   └── com.qrlogi.api.QrLogiApplicationTests.html
│   │   │           ├── css
│   │   │           │   ├── base-style.css
│   │   │           │   └── style.css
│   │   │           ├── index.html
│   │   │           ├── js
│   │   │           │   └── report.js
│   │   │           └── packages
│   │   │               └── com.qrlogi.api.html
│   │   ├── resolvedMainClassName
│   │   ├── resources
│   │   │   └── main
│   │   │       ├── application-docker.yml
│   │   │       ├── application.yml
│   │   │       ├── sql
│   │   │       │   └── erd.sql
│   │   │       ├── static
│   │   │       │   └── favicon.ico
│   │   │       └── templates
│   │   ├── test-results
│   │   │   └── test
│   │   │       ├── TEST-com.qrlogi.api.QrLogiApplicationTests.xml
│   │   │       └── binary
│   │   │           ├── output.bin
│   │   │           ├── output.bin.idx
│   │   │           └── results.bin
│   │   └── tmp
│   │       ├── bootJar
│   │       │   └── MANIFEST.MF
│   │       ├── compileJava
│   │       │   └── previous-compilation-data.bin
│   │       ├── compileTestJava
│   │       │   └── previous-compilation-data.bin
│   │       └── test
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
├── build
│   ├── build.gradle
│   ├── libs
│   │   ├── QRLogi-0.0.1-SNAPSHOT-plain.jar
│   │   └── QRLogi-0.0.1-SNAPSHOT.jar
│   ├── reports
│   │   └── problems
│   │       └── problems-report.html
│   ├── resolvedMainClassName
│   └── tmp
│       ├── bootJar
│       │   └── MANIFEST.MF
│       └── jar
│           └── MANIFEST.MF
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
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── http
│   ├── buyerRegisterRequest.http
│   └── signRequest.http
├── infra
│   └── docker-compose.yml
└── settings.gradle

142 directories, 122 files

```
<br><br>
**Docker 설정**
- MySql
- Master-Slave구조 (springApp, master, slave)