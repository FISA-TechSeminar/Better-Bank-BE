package com.practice.thebetterbank.service.transactionhistory;

import com.practice.thebetterbank.entity.TransactionHistory;
import com.practice.thebetterbank.repository.transactionhistory.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
