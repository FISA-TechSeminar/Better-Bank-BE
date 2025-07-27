package com.practice.thebetterbank.controller.dto;

import com.practice.thebetterbank.entity.TransactionHistory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class TransactionHistoryListDTO {
    private Page<TransactionHistory> transactionHistories;
    private Long balance;

    @Builder
    public TransactionHistoryListDTO(Page<TransactionHistory> transactionHistories, Long balance) {
        this.transactionHistories = transactionHistories;
        this.balance = balance;
    }
}
