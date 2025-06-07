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
QRLogi/
├── api
│   ├── Dockerfile
│   ├── build
│   ├── build.gradle
│   └── src
├── build
│   ├── libs
│   ├── reports
│   ├── resolvedMainClassName
│   └── tmp
├── domain-buyer
│   ├── build.gradle
│   └── src
├── domain-order
│   ├── build.gradle
│   └── src
├── domain-orderitem
│   ├── build.gradle
│   └── src
├── domain-payment
│   ├── build.gradle
│   └── src
├── domain-product
│   ├── build.gradle
│   └── src
├── domain-qrscanlog
│   ├── build.gradle
│   └── src
├── domain-shipment
│   ├── build.gradle
│   └── src
├── domain-shipmentitem
│   ├── build.gradle
│   └── src
├── domain-user
│   ├── build.gradle
│   └── src
├── gradle
│   └── wrapper
├── gradlew
├── gradlew.bat
├── http
├── infra
│   └── docker-compose.yml
└── settings.gradle

```
<br><br>
**Docker 설정**
- MySql
- Master-Slave구조 (springApp, master, slave)