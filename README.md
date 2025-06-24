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
a. 바이어 주문 등록
    * 주문 정보 및 품목 수량 입력

b. QR코드와 바코드 이미지 생성
    * 물품한개당 고유 serial ID 생성
    * QR 코드 및 바코드 이미지 생성
    
c. 출력용 파일 생성
    * 생성된 이미지를 프린터용지사이즈에 맞도록하여 PDF 파일로 변환
    * 작업자가 인쇄하여 물품에 부착

d. 스캔 및 출고 검수
    * 작업자가 QR / 바코드 스캔
    * 실시간 출고 수량 기록
    * 중복 스캔 방지(동시성해결, 로그 저장)

e. Google 스프레드시트로 보고
    * 실제 적재수량(스캔됨)과 주문 수량 비교
    * 검수 결과 자동 정리 후 스프레드시트 업로드
    * 바이어에게 공유 가능
```
<br>

- 주문아이템에 대한 일련번호 생성 및 qr 생성 상세
```aiignore
1. 제품 등록 → 주문 발생 → QR/일련번호 생성   :  Spring REST API + DB 트랜잭션 처
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
