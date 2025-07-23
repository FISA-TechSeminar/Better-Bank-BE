package com.practice.thebetterbank.service;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.TransactionHistory;
import com.practice.thebetterbank.entity.type.TransactionType;
import com.practice.thebetterbank.repository.account.AccountRepository;
import com.practice.thebetterbank.repository.transactionhistory.TransactionHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@Slf4j
public class TransactionHistoryTest {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    private final Random random = new Random();

    @Test
    public void transactionHistoryInputDummys() {
        for (long accountId = 1; accountId <= 12; accountId++) {
            int count = (accountId <= 8) ? 10 : 1000;
            Account account = accountRepository.findById(accountId).get();

            List<TransactionHistory> histories = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int amount = random.nextInt(1_000_000 - 10 + 1) + 10;
                TransactionType type = random.nextBoolean() ? TransactionType.DEPOSIT : TransactionType.TRANSFER;
                LocalDate date = LocalDate.now().minusDays(random.nextInt(90)); // 최근 3개월 랜덤
                String targetAccount = "10001234" + String.format("%04d", random.nextInt(10000));

                TransactionHistory history = TransactionHistory.builder()
                        .account(account)
                        .amount(amount)
                        .transactionName("더미거래-" + (i + 1))
                        .transactionDate(date)
                        .targetAccountNumber(targetAccount)
                        .transactionType(type)
                        .build();

                histories.add(history);

                if (histories.size() % 500 == 0) { // 메모리 방지용 batch insert
                    transactionHistoryRepository.saveAll(histories);
                    transactionHistoryRepository.flush();
                    histories.clear();
                }
            }

            if (!histories.isEmpty()) {
                transactionHistoryRepository.saveAll(histories);
                transactionHistoryRepository.flush();
            }

            System.out.println(accountId + "번 계좌 더미 데이터 " + count + "개 생성 완료");
        }
    }
}
