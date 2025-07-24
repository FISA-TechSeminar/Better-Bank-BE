package com.practice.thebetterbank.service.transactionhistory;

import com.practice.thebetterbank.entity.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionHistoryService {
    Page<TransactionHistory> getTransactionHistoryByAccountId(Long accountId, Pageable pageable);
}
