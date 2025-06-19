**진행중**
<br><br><br>

**서비스플로우** 
- QR 기반 스캔 처리 → 수량 변화 감지 → 추적 로그 저장
```aiignore
[회원/바이어/상품 등록]
        ↓
[주문(Order) 생성]
        ↓
[출고 등록 (Shipment)]
        ↓
[출고 항목 생성 (ShipmentItem)]
        ↓
[QR 스캔 (→ OrderItem.shippedQty 증가)]
        ↓
[전체 출고 완료 판단 → 상태 변경]
        ↓
[검수 API로 검수자 기록 → 최종 완료]
```

<br>

- 구상안

```aiignore
1) 주문 승인 시, 각 주문 항목에 대해 제품명, 수량, 박스 단위 정보를 등록

2) 등록된 정보를 바탕으로 QR코드가 생성되고, 출력해 출고 현장에 부착

3) 출고 담당자는 박스에 부착된 QR코드를 스캔

4) 스캔 시마다 해당 박스에 매핑된 orderItem의 출고 수량(shippedQty)이 누적

5) 각 스캔 내역은 ShipmentLog에 저장되어 기록 유지

6) 주문 수량(orderedQty)과 출고 수량(shippedQty)이 같아지면 상태를 SHIPPED로 변경

7) 출고 완료 이후, 검수 기록은 구글 스프레드시트 또는 시스템 내에서 확인 가능

8) 바이어에게 출고 이력 및 검수 내역 전달
```
<br>
- 주문아이템에 대한 일련번호 생성 및 qr 생성 상세

```aiignore
1. 제품 등록 → 주문 발생 → QR/일련번호 생성   :  Spring REST API + DB 트랜잭션 처리
2. 스캔/검수 → 실시간 처리, 작업자 다수 참여    :  Kafka
```
<br>

- Inspection 상세 

```aiignore
  1. 작업자가 출고 현장에서 QR코드를 스캔
  2. 출고 수량이 주문 수량과 일치하면 상태를 “완료”로 변경
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
│           │               ├── inspection
│           │               │   ├── dto
│           │               │   │   └── ScanRequest.java
│           │               │   ├── entity
│           │               │   │   ├── ScanLog.java
│           │               │   │   └── ScanStatus.java
│           │               │   ├── repository
│           │               │   │   └── ScanLogRepository.java
│           │               │   └── service
│           │               │       └── ScanService.java
│           │               ├── order
│           │               │   ├── dto
│           │               │   │   ├── OrderRequest.java
│           │               │   │   └── OrderResponse.java
│           │               │   ├── entity
│           │               │   │   ├── OrderStatus.java
│           │               │   │   └── Orders.java
│           │               │   ├── repository
│           │               │   │   └── OrderRepository.java
│           │               │   └── service
│           │               │       ├── OrderService.java
│           │               │       └── OrderServiceImpl.java
│           │               ├── orderitem
│           │               │   ├── dto
│           │               │   │   ├── OrderItemDTO.java
│           │               │   │   └── OrderItemRequest.java
│           │               │   ├── entity
│           │               │   │   ├── OrderItem.java
│           │               │   │   └── ShipmentStatus.java
│           │               │   ├── repository
│           │               │   │   └── OrderItemRepository.java
│           │               │   └── service
│           │               │       └── OrderItemService.java
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
│           │               │   │   └── ProductDTO.java
│           │               │   ├── entity
│           │               │   │   └── Product.java
│           │               │   ├── repository
│           │               │   │   └── ProductRepository.java
│           │               │   └── service
│           │               │       └── ProductService.java
│           │               ├── shipment
│           │               │   ├── dto
│           │               │   ├── entity
│           │               │   │   ├── ShipmentItem.java
│           │               │   │   └── Shipments.java
│           │               │   ├── repository
│           │               │   └── service
│           │               │       └── ShipmentService.java
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


```
<br><br><br>
**Docker**
- MySql
- Master-Slave구조 (springApp, master, slave)

<br><br>
**Snowflake**
- orderitem일련번호에 적용

<br><br>

**도메인구성**
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
