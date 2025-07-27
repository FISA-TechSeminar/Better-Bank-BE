package com.practice.thebetterbank.service.transactionhistory;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionHistoryService {
    Page<TransactionHistory> getTransactionHistoryByAccountId(Long accountId, Pageable pageable);

    void receiveInterest( Account account, long interest, LocalDate today, String targetAccountNumber);
}
