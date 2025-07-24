package com.practice.thebetterbank.repository.transactionhistory;

import com.practice.thebetterbank.entity.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    Page<TransactionHistory> findAllByAccountId(Long accountId, Pageable pageable);

}
