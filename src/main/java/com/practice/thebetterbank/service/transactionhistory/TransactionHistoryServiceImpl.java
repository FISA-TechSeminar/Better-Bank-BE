package com.practice.thebetterbank.service.transactionhistory;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.TransactionHistory;
import com.practice.thebetterbank.entity.type.TransactionType;
import com.practice.thebetterbank.repository.transactionhistory.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public Page<TransactionHistory> getTransactionHistoryByAccountId(Long accountId, Pageable pageable) {
        return transactionHistoryRepository.findAllByAccountId(accountId, pageable);
    }

    @Override
    public void receiveInterest(Account account, long interest, LocalDate today, String targetAccountNumber) {
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .transactionDate(today)
                .transactionName("조은은행 이자")
                .amount(interest)
                .targetAccountNumber(targetAccountNumber)
                .transactionType(TransactionType.DEPOSIT)
                .account(account)
                .build();
        transactionHistoryRepository.save(transactionHistory);
    }
}
