package com.practice.thebetterbank.service.transactionhistory;

import com.practice.thebetterbank.entity.TransactionHistory;

import java.util.List;

public interface TransactionHistoryService {
    List<TransactionHistory> getTransactionHistoryByAccountId(Long accountId);
}
