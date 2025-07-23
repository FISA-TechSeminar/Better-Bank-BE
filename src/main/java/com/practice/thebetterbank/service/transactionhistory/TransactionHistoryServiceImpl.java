package com.practice.thebetterbank.service.transactionhistory;

import com.practice.thebetterbank.entity.TransactionHistory;
import com.practice.thebetterbank.repository.transactionhistory.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public List<TransactionHistory> getTransactionHistoryByAccountId(Long accountId) {
        return transactionHistoryRepository.findAllByAccountId(accountId);
    }
}
