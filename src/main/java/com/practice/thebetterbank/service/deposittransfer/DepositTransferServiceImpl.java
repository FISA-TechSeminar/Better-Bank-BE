package com.practice.thebetterbank.service.deposittransfer;

import com.practice.thebetterbank.controller.dto.DepositTransferDTO;
import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.TransactionHistory;
import com.practice.thebetterbank.entity.type.TransactionType;
import com.practice.thebetterbank.repository.account.AccountRepository;
import com.practice.thebetterbank.repository.transactionhistory.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DepositTransferServiceImpl implements DepositTransferService {

    private final AccountRepository accountRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    @Transactional
    public DepositTransferDTO deposit(DepositTransferDTO dto) {
        Account toAccount = accountRepository.findByAccountNumber(dto.getTargetAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("입금 대상 계좌를 찾을 수 없습니다."));

        toAccount.increaseBalance(dto.getAmount());

        // 거래 내역 저장
        TransactionHistory history = TransactionHistory.builder()
                .account(toAccount)
                .amount(dto.getAmount())
                .transactionName(dto.getTransactionName())
                .transactionDate(LocalDate.now())
                .targetAccountNumber(toAccount.getAccountNumber())
                .transactionType(TransactionType.DEPOSIT)
                .build();

        transactionHistoryRepository.save(history);

        return DepositTransferDTO.builder()
                .targetAccountNumber(toAccount.getAccountNumber())
                .amount(dto.getAmount())
                .transactionName(dto.getTransactionName())
                .build();
    }

    @Override
    @Transactional
    public DepositTransferDTO transfer(DepositTransferDTO dto) {
        Long fromAccountId = Long.valueOf(dto.getAccountId());

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("출금 계좌를 찾을 수 없습니다."));

        Account toAccount = accountRepository.findByAccountNumber(dto.getTargetAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("입금 계좌를 찾을 수 없습니다."));

        if (fromAccount.getBalance() < dto.getAmount()) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        fromAccount.decreaseBalance(dto.getAmount());
        toAccount.increaseBalance(dto.getAmount());

        // 거래 내역 저장
        TransactionHistory history = TransactionHistory.builder()
                .account(fromAccount)
                .amount(dto.getAmount())
                .transactionName(dto.getTransactionName())
                .transactionDate(LocalDate.now())
                .targetAccountNumber(toAccount.getAccountNumber())
                .transactionType(TransactionType.TRANSFER)
                .build();

        transactionHistoryRepository.save(history);

        return DepositTransferDTO.builder()
                .accountId(fromAccount.getId().toString())
                .targetAccountNumber(toAccount.getAccountNumber())
                .amount(dto.getAmount())
                .transactionName(dto.getTransactionName())
                .build();
    }
}