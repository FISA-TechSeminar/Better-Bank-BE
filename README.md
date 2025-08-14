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

이번 프로젝트를 통해 눈에 보이지 않는 백엔드 구조와 성능이 더 중요하다는 것을 깨달았습니다. 계좌 조회·거래 내역·이자 수령 API를 구현하며 DB 쿼리 최적화와 트랜잭션 관리를 깊이 고민했습니다. 

특히 **QueryDSL을 활용한 동적 쿼리**로 복잡한 거래 내역 조회를 효율적으로 처리하고, **Redis 캐싱**을 통해 자주 조회되는 계좌 정보와 회원 데이터의 응답 속도를 크게 개선했습니다. 대용량 거래 데이터 처리 시 **페이징 처리**와 **트랜잭션 격리 레벨** 설정으로 데이터 일관성을 보장하면서도 성능을 최적화할 수 있었습니다.

또한 이자 계산 로직에서 **동시성 제어**와 **중복 처리 방지** 메커니즘을 구현하여 금융 시스템의 핵심인 데이터 무결성을 확보했습니다.

💬 문의·협업·피드백은 PR/이슈/디스커션으로 언제든 환영합니다!
