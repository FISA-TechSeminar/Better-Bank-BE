package com.practice.thebetterbank.repository.transactionhistory;

import com.practice.thebetterbank.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository  extends JpaRepository<TransactionHistory, Long> {
}
