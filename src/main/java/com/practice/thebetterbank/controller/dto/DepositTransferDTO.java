package com.practice.thebetterbank.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositTransferDTO {
    // 입금용 필드
    private String fromAccountId;         // 출금 계좌 ID (입금 시 사용)
    private String targetAccountNumber;   // 입금 대상 계좌 번호 (입금 시 사용)

    // 송금용 필드
    private String accountId;             // 출금 계좌 ID (송금 시 사용)

    private Long amount;                  // 금액
    private String transactionName;      // 거래명
}
