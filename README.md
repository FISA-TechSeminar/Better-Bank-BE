# 🏦 Better Bank - Backend

금융 기술 세미나에서 3-Tier 아키텍처의 병목 구간을 찾아 고트래픽 부하 테스트를 하기 위해 만든 Spring Boot 백엔드 API 서버입니다.

테스트 범위를 명확히 하기 위해 계좌 목록 조회, 계좌 상세 내역 조회(트랜잭션), 매일 이자 받기 3가지 기능만 제공합니다.

- **Presentation**: 프론트엔드 (React) – 사용자 입력/표시  
- **Application**: 본 프로젝트 (Spring Boot) – 비즈니스 로직/권한/검증  
- **Database**: RDBMS – 계좌/거래 데이터 영속화  

➡️ 계층 분리로 보안/유지보수/확장성 확보 → 금융 도메인 표준 구조

## 핵심 기능
• 👤 **회원 관리**: 회원 정보 조회 및 관리  
• 💳 **계좌 목록 조회**: `/members/:memberId/accounts`  
• 📄 **계좌 상세 내역 조회**: `/transaction/:accountId`  
• 💰 **매일 이자 받기**: `/interest/receive` (이자 계산 및 지급)  
• 🔄 **입출금/송금**: 계좌 간 자금 이체 처리  
• 📊 **거래 내역 관리**: QueryDSL을 활용한 효율적인 거래 내역 조회  

## 기술 스택 🛠
- **Spring Boot 3.5.3** (메인 프레임워크)
- **Spring Data JPA** + **QueryDSL** (데이터 액세스)  
- **MySQL** (운영 DB) / **H2** (테스트 DB)  
- **Gradle** (빌드 도구)  
- **Java 17** (개발 언어)  

프론트엔드는 별도 프로젝트 [Better-Bank-FE](https://github.com/FISA-TechSeminar/Better-Bank-FE)에서 확인하실 수 있습니다. 개발 중엔 CORS 설정을 통해 연동됩니다.

## 📁 프로젝트 구조

```
Better-Bank-BE/
├── 📄 build.gradle
├── 📁 gradle/
│   └── 📁 wrapper/
│       ├── 📄 gradle-wrapper.jar
│       └── 📄 gradle-wrapper.properties
├── 📄 gradlew
├── 📄 gradlew.bat
├── 📄 settings.gradle
└── 📁 src/
    ├── 📁 main/
    │   ├── 📁 generated/
    │   │   └── 📁 com/
    │   │       └── 📁 practice/
    │   │           └── 📁 thebetterbank/
    │   │               └── 📁 entity/
    │   │                   ├── 📄 QAccount.java
    │   │                   ├── 📄 QInterestHistory.java
    │   │                   ├── 📄 QMember.java
    │   │                   └── 📄 QTransactionHistory.java
    │   ├── 📁 java/
    │   │   └── 📁 com/
    │   │       └── 📁 practice/
    │   │           └── 📁 thebetterbank/
    │   │               ├── 📁 config/
    │   │               │   ├── 📄 QueryDSLConfig.java
    │   │               │   └── 📄 WebConfig.java
    │   │               ├── 📁 controller/
    │   │               │   ├── 📄 AccountController.java
    │   │               │   ├── 📄 DepositTransferController.java
    │   │               │   ├── 📁 dto/
    │   │               │   │   ├── 📄 AccountDTO.java
    │   │               │   │   ├── 📄 DepositTransferDTO.java
    │   │               │   │   ├── 📄 InterestDTO.java
    │   │               │   │   ├── 📄 MemberDTO.java
    │   │               │   │   ├── 📄 ReceiveInterestDTO.java
    │   │               │   │   ├── 📄 ResultDTO.java
    │   │               │   │   └── 📄 TransactionHistoryListDTO.java
    │   │               │   ├── 📄 InterestHistoryController.java
    │   │               │   ├── 📄 MemberController.java
    │   │               │   ├── 📄 TestController.java
    │   │               │   └── 📄 TransactionHistoryController.java
    │   │               ├── 📁 entity/
    │   │               │   ├── 📄 Account.java
    │   │               │   ├── 📄 InterestHistory.java
    │   │               │   ├── 📄 Member.java
    │   │               │   ├── 📄 TransactionHistory.java
    │   │               │   └── 📁 type/
    │   │               │       └── 📄 TransactionType.java
    │   │               ├── 📁 repository/
    │   │               │   ├── 📁 account/
    │   │               │   │   └── 📄 AccountRepository.java
    │   │               │   ├── 📁 interesthistory/
    │   │               │   │   ├── 📄 InterestHistoryQueryDSL.java
    │   │               │   │   ├── 📄 InterestHistoryQueryDSLImpl.java
    │   │               │   │   └── 📄 InterestHistoryRepository.java
    │   │               │   ├── 📁 member/
    │   │               │   │   ├── 📄 MemberQueryDSL.java
    │   │               │   │   ├── 📄 MemberQueryDSLImpl.java
    │   │               │   │   └── 📄 MemberRepository.java
    │   │               │   └── 📁 transactionhistory/
    │   │               │       ├── 📄 TransactionHistoryQueryDSL.java
    │   │               │       ├── 📄 TransactionHistoryQueryDSLImpl.java
    │   │               │       └── 📄 TransactionHistoryRepository.java
    │   │               ├── 📁 service/
    │   │               │   ├── 📁 account/
    │   │               │   │   ├── 📄 AccountService.java
    │   │               │   │   └── 📄 AccountServiceImpl.java
    │   │               │   ├── 📁 deposittransfer/
    │   │               │   │   ├── 📄 DepositTransferService.java
    │   │               │   │   └── 📄 DepositTransferServiceImpl.java
    │   │               │   ├── 📁 interesthistory/
    │   │               │   │   ├── 📄 InterestHistoryService.java
    │   │               │   │   └── 📄 InterestHistoryServiceImpl.java
    │   │               │   ├── 📁 transactionhistory/
    │   │               │   │   ├── 📄 TransactionHistoryService.java
    │   │               │   │   └── 📄 TransactionHistoryServiceImpl.java
    │   │               │   └── 📁 user/
    │   │               │       ├── 📄 MemberService.java
    │   │               │       └── 📄 MemberServiceImpl.java
    │   │               └── 📄 TheBetterBankApplication.java
    │   └── 📁 resources/
    │       ├── 📄 application.yml
    │       └── 📁 templates/
    │           └── 📄 test.html
    └── 📁 test/
        └── 📁 java/
            └── 📁 com/
                └── 📁 practice/
                    └── 📁 thebetterbank/
                        ├── 📁 repository/
                        │   └── 📄 InterestHistoryQueryDSLImplTest.java
                        ├── 📁 service/
                        │   ├── 📄 AccountServiceTest.java
                        │   ├── 📄 DepositTransferServiceTest.java
                        │   ├── 📄 InterestHistoryServiceTest.java
                        │   ├── 📄 MemberServiceTest.java
                        │   ├── 📄 TransactionHistoryQueryDSLTest.java
                        │   └── 📄 UserServiceTest.java
                        └── 📄 TheBetterBankApplicationTests.java
```

## 🔧 API 문서

### 회원 관리 API

#### `GET /member/{id}`
회원 정보를 조회하고 세션을 생성합니다.
- **Parameters**: `id` (Long) - 회원 ID
- **Response**: `MemberDTO`
- **Status**: `200 OK` / `400 BAD REQUEST`

```json
{
  "status": "OK",
  "message": "회원 정보를 불러왔습니다!",
  "data": {
    "id": 1,
    "memberName": "홍길동"
  }
}
```

### 계좌 관리 API

#### `GET /members/{memberId}/accounts`
특정 회원의 모든 계좌 목록을 조회합니다.
- **Parameters**: `memberId` (Long) - 회원 ID
- **Response**: `List<AccountDTO>`
- **Status**: `200 OK`

```json
{
  "status": "OK",
  "message": "계좌 목록 조회 성공",
  "data": [
    {
      "accountId": 1,
      "accountNumber": "183-917375-18401",
      "balance": 1500000,
      "interestRate": 2.5
    }
  ]
}
```

#### `GET /accounts/{accountId}`
특정 계좌의 상세 정보를 조회합니다.
- **Parameters**: `accountId` (Long) - 계좌 ID
- **Response**: `Account`
- **Status**: `200 OK` / `404 NOT FOUND`

### 거래 관리 API

#### `POST /account/deposit`
계좌에 입금을 처리합니다.
- **Request Body**: `DepositTransferDTO`
- **Response**: `DepositTransferDTO`
- **Status**: `200 OK`

```json
{
  "accountNumber": "183-917375-18401",
  "amount": 100000,
  "description": "급여 입금"
}
```

#### `POST /account/transfer`
계좌 간 송금을 처리합니다.
- **Request Body**: `DepositTransferDTO`
- **Response**: `DepositTransferDTO`
- **Status**: `200 OK`

```json
{
  "fromAccountNumber": "183-917375-18401",
  "toAccountNumber": "183-917375-18402",
  "amount": 50000,
  "description": "용돈 송금"
}
```

#### `GET /transactionhistory/{accountId}`
특정 계좌의 거래 내역을 페이징하여 조회합니다.
- **Parameters**: 
  - `accountId` (Long) - 계좌 ID
  - `page` (int, default: 0) - 페이지 번호
  - `size` (int, default: 10) - 페이지 크기
- **Response**: `TransactionHistoryListDTO`
- **Status**: `200 OK`

```json
{
  "status": "OK",
  "message": "계좌 거래내역을 불러왔습니다!",
  "data": {
    "balance": 1500000,
    "transactionHistories": {
      "content": [
        {
          "transactionId": 1,
          "amount": 100000,
          "transactionType": "DEPOSIT",
          "description": "급여 입금",
          "transactionDate": "2024-01-15T10:30:00"
        }
      ],
      "pageable": {...},
      "totalElements": 25
    }
  }
}
```

### 이자 관리 API

#### `GET /accounts/{accountId}/interest`
계좌의 이자 정보를 조회합니다.
- **Parameters**: `accountId` (Long) - 계좌 ID
- **Response**: `InterestDTO`
- **Status**: `202 ACCEPTED` / `400 BAD REQUEST`

```json
{
  "status": "ACCEPTED",
  "message": "이자 조회 성공",
  "data": {
    "accountId": 1,
    "lastInterestDate": "2024-01-14",
    "interestAmount": 2500
  }
}
```

#### `GET /accounts/{accountId}/receiveinterest`
계좌의 이자를 수령합니다.
- **Parameters**: `accountId` (Long) - 계좌 ID
- **Response**: `ReceiveInterestDTO`
- **Status**: `202 ACCEPTED` / `400 BAD REQUEST`

```json
{
  "status": "ACCEPTED",
  "message": "이자 받기 성공",
  "data": {
    "accountId": 1,
    "interestAmount": 2500,
    "newBalance": 1502500,
    "receivedDate": "2024-01-15"
  }
}
```

### 공통 응답 형식
모든 API는 `ResultDTO` 형태로 응답합니다:
```json
{
  "status": "HTTP_STATUS",
  "message": "응답 메시지",
  "data": "실제 데이터 (선택적)"
}
```

---

# 🚀 Redis를 활용한 캐싱 전략

`매일 이자 받기` 기능에 Redis를 활용하여 **응답 속도 개선**, **DB 부하 완화**, **중복 수령 방지**를 달성했습니다.

이자 계산과 수령은 하루에 한 번만 가능해야 하며, **같은 계좌에 대해 동시에 여러 요청이 들어오는 상황을 안전하게 처리**합니다.

---

### 적용 대상 기능

- `/accounts/{accountId}/interest` – 이자 계산 조회
- `/accounts/{accountId}/receiveinterest` – 이자 수령 처리

이 기능은 모두 **매일 반복해서 호출되며**, **응답의 정확성과 속도 모두 중요한 고빈도 API**입니다.

---

### 캐싱 전략

### 📍 캐싱 방식: **Cache-Aside + Write-Through**

| 전략 구분 | 설명 | 적용 대상 |
| --- | --- | --- |
| **읽기 (조회)** | Cache-Aside (Look-Aside) 패턴 | `/interest` |
| **쓰기 (수령)** | Write-Through 패턴 | `/receiveinterest` |

---

### ✅ Cache-Aside 패턴 (조회 시)

```java
// 이자 캐시 조회 → 없으면 계산 후 저장
InterestDTO cached = redisTemplate.opsForValue().get("interest:calc:" + accountId);
if (cached == null) {
    InterestDTO interest = calculateInterest(...);
    redisTemplate.opsForValue().set(key, interest, ttl, TimeUnit.SECONDS);
}

```

- 애플리케이션이 먼저 Redis에 접근
- 없으면 DB에서 계산한 뒤 캐시에 저장
- TTL(Time-To-Live)은 **오늘 자정까지** 설정하여 다음 날 자동 갱신

> 캐시 장애 시에도 DB 조회로 복구 가능 → 안정성 확보
> 

---

### ✅ Write-Through 패턴 (수령 시)

```java
// 이자 수령 성공 시, 캐시를 '0원'으로 갱신
InterestDTO zeroInterest = InterestDTO.builder()
    .accountId(accountId)
    .lastInterestDate(today)
    .interestAmount(0L)
    .build();

redisTemplate.opsForValue().set(
    "interest:calc:" + accountId,
    zeroInterest,
    getSecondsUntilMidnight(),
    TimeUnit.SECONDS
);

```

- DB 저장과 동시에 캐시도 업데이트
- 이후 조회 시 이자 금액이 `0L`이므로 재수령 차단 가능

---

### 🔒 Redis 기반 동시성 제어

### 🚨 문제 상황 예시

동일 계좌로 동시에 수령 요청이 들어올 경우:

```
1. 모바일과 웹에서 동시에 '지금 이자 받기' 클릭
2. 두 요청 모두 '아직 수령 안 함'으로 판단 → 중복 지급 발생
```

### 🔐 해결: Redis 분산락 (SETNX)

```java
Boolean lockAcquired = redisTemplate.opsForValue()
    .setIfAbsent("interest:lock:" + accountId, "LOCK", 5, TimeUnit.SECONDS);

if (!lockAcquired) return null; // 락 획득 실패 시 즉시 차단

try {
    // 이자 지급 로직
} finally {
    redisTemplate.delete("interest:lock:" + accountId); // 락 해제
}

```

- `setIfAbsent()` = Redis의 SETNX + TTL
- 락 키: `interest:lock:{accountId}`
- TTL: 5초 (DB 트랜잭션 처리 시간 기준)
- `finally` 블록에서 락 해제 보장

---

### 🧪 캐시 정합성 전략 비교

| 전략 | 방식 | 장점 | 단점 |
| --- | --- | --- | --- |
| **Update** | 캐시를 '0원'으로 덮어씀 | 고정된 캐시 히트율, 빠른 응답 | 락 필요, 구현 복잡 |
| **Evict** | 캐시 키 삭제 | 동시성 안전, 간단한 구현 | 삭제 후 첫 조회 시 DB 조회 발생 |

```java
// Evict 방식 구현 예시
public void evictCachedInterest(Long accountId) {
    redisTemplate.delete("interest:calc:" + accountId);
}
```

> 두 전략 모두 테스트 후 비교 → 금융 서비스에서는 Cache Evict 전략이 더 적합하다는 결론
> 

---

### 📈 성능 테스트 결과 (K6 + Prometheus + Grafana)

| 항목 | 캐시 미적용 | Cache Update | Cache Evict |
| --- | --- | --- | --- |
| 총 처리 요청 수 | 23,715건 | 59,001건 | 59,254건 |
| 평균 처리량 | 39.0 req/s | 99.2 req/s | 96.7 req/s |
| 에러율 | 0% | 0% | 0% |
- 캐싱 도입으로 **최대 154% 성능 향상**
- 안정성과 응답 속도 모두 개선

---

### 기술적 인사이트

- **단순 조회 캐싱**이 아닌, **비즈니스 로직까지 고려한 조건부 캐싱**이 중요
- Redis는 단순히 빠르기만 한 게 아니라, **정합성 제어를 위한 수단**으로도 강력
- **Cache Eviction + 분산락 조합**은 금융 시스템의 정합성과 안정성 확보에 효과적

---

### 📌 Redis 관련 주요 파일

| 파일 | 설명 |
| --- | --- |
| `RedisConfig.java` | Redis 연결 설정, Jackson 직렬화 |
| `InterestHistoryServiceImpl.java` | 캐싱 로직, 락 처리, TTL 적용 |
| `InterestDTO.java` | Redis에 저장되는 이자 계산 결과 구조 |

---

### 📁 Redis 캐시 키 구조

| 용도 | 키 형식 | TTL |
| --- | --- | --- |
| 이자 계산 결과 | `interest:calc:{accountId}` | 매일 자정까지 |
| 수령 락 제어 | `interest:lock:{accountId}` | 5초 |


---

### 회고

> 이번 프로젝트를 통해 눈에 보이지 않는 백엔드 구조와 성능이 더 중요하다는 것을 깨달았습니다. 계좌 조회·거래 내역·이자 수령 API를 구현하며 DB 쿼리 최적화와 트랜잭션 관리를 깊이 고민했습니다.
> 
> 
> 특히 **QueryDSL을 활용한 동적 쿼리**로 복잡한 거래 내역 조회를 효율적으로 처리하고, **Redis 캐싱**을 통해 자주 조회되는 계좌 정보와 회원 데이터의 응답 속도를 크게 개선했습니다. 대용량 거래 데이터 처리 시 **페이징 처리**와 **트랜잭션 격리 레벨** 설정으로 데이터 일관성을 보장하면서도 성능을 최적화할 수 있었습니다.
> 
> 또한 이자 계산 로직에서 **동시성 제어**와 **중복 처리 방지** 메커니즘을 구현하여 금융 시스템의 핵심인 데이터 무결성을 확보했습니다.
> 
> 마지막으로, Redis를 단순 캐시 용도가 아닌 **정합성과 안정성을 확보하는 핵심 기술 요소**로 활용해 본 것이 큰 수확이었습니다.
> 
> 캐싱 전략은 Cache-Aside + Write-Through 방식으로 설계하되, **Cache Update**와 **Cache Eviction** 두 가지 방식을 실제 구현하고 K6로 부하 테스트해 성능을 정량적으로 비교했습니다.
> 
> 그 결과, 단순한 구현과 완벽한 정합성을 갖춘 **Eviction 전략**이 금융 시스템에 더 적합하다는 결론을 얻었습니다.
> 
> 또한 **setIfAbsent 기반 분산락**을 통해 동시에 이자 수령 요청이 들어오는 상황에서도 중복 지급 없이 안정적으로 처리되도록 구현했으며, 이 과정에서 TTL, 락 범위, 캐시 키 설계 등의 세부 제어가 얼마나 중요한지를 몸소 체감할 수 있었습니다.
> 
> 이 프로젝트는 단순히 조회 속도를 높이는 것 뿐만 아니라, **정합성을 해치지 않는 선에서 성능을 최적화하는 기술적 판단력**을 기를 수 있었던 값진 경험이었습니다.
> 

---


💬 문의·협업·피드백은 PR/이슈/디스커션으로 언제든 환영합니다!
