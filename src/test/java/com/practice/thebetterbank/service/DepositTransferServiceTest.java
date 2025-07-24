package com.practice.thebetterbank.service;

import com.practice.thebetterbank.controller.dto.DepositTransferDTO;
import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.repository.account.AccountRepository;
import com.practice.thebetterbank.service.deposittransfer.DepositTransferService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Slf4j
@Transactional
public class DepositTransferServiceTest {

    @Autowired
    private DepositTransferService depositTransferService;

    @Autowired
    private AccountRepository accountRepository;

    private Account sender;
    private Account receiver;

    @BeforeEach
    void setUp() {
        // 기본 계좌 2개 생성
        sender = accountRepository.save(Account.builder()
                .accountNumber("10000001")
                .balance(100000L)
                .build());

        receiver = accountRepository.save(Account.builder()
                .accountNumber("20000002")
                .balance(50000L)
                .build());
    }

    @Test
    void 입금_성공_테스트() {
        // given
        DepositTransferDTO dto = DepositTransferDTO.builder()
                .targetAccountNumber(receiver.getAccountNumber())
                .amount(30000L)
                .transactionName("테스트 입금")
                .build();

        // when
        DepositTransferDTO result = depositTransferService.deposit(dto);

        // then
        Account updated = accountRepository.findByAccountNumber(receiver.getAccountNumber()).get();
        assertThat(updated.getBalance()).isEqualTo(80000L);
        assertThat(result.getTargetAccountNumber()).isEqualTo("20000002");
        assertThat(result.getAmount()).isEqualTo(30000L);
    }

    @Test
    void 송금_성공_테스트() {
        // given
        DepositTransferDTO dto = DepositTransferDTO.builder()
                .accountId(sender.getId().toString())
                .targetAccountNumber(receiver.getAccountNumber())
                .amount(20000L)
                .transactionName("테스트 송금")
                .build();

        // when
        DepositTransferDTO result = depositTransferService.transfer(dto);

        // then
        Account fromUpdated = accountRepository.findById(sender.getId()).get();
        Account toUpdated = accountRepository.findByAccountNumber(receiver.getAccountNumber()).get();

        assertThat(fromUpdated.getBalance()).isEqualTo(80000L);
        assertThat(toUpdated.getBalance()).isEqualTo(70000L);
        assertThat(result.getTransactionName()).isEqualTo("테스트 송금");
    }

    @Test
    void 송금_실패_잔액부족_테스트() {
        // given
        DepositTransferDTO dto = DepositTransferDTO.builder()
                .accountId(sender.getId().toString())
                .targetAccountNumber(receiver.getAccountNumber())
                .amount(200000L) // sender는 10만원 보유
                .transactionName("실패 송금")
                .build();

        // when + then
        assertThatThrownBy(() -> depositTransferService.transfer(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔액이 부족합니다.");
    }
}