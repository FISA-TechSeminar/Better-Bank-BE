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
    private static final List<String> NAMES = List.of("최홍석", "홍혜원", "황지환", "김혜경", "이제현", "김동민", "이정이");
    private static final List<String> SHOPS = List.of("엽기떡볶이", "이디야커피", "GS25", "스타벅스", "배달의민족", "맘스터치", "던킨도너츠");

    private String getRandomName() {
        return NAMES.get(random.nextInt(NAMES.size()));
    }

    private String getRandomShop() {
        return SHOPS.get(random.nextInt(SHOPS.size()));
    }
    @Test
    public void transactionHistoryTest() {

        for (long accountId = 1; accountId <= 12; accountId++) {
            int count = (accountId <= 8) ? 10 : 1000;
            Account account = accountRepository.findById(accountId).orElseThrow();

            List<TransactionHistory> histories = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int amount = random.nextInt(1_000_000 - 10 + 1) + 10;
                TransactionType type = random.nextBoolean() ? TransactionType.DEPOSIT : TransactionType.TRANSFER;
                LocalDate date = LocalDate.now().minusDays(random.nextInt(90)); // 최근 3개월 랜덤
                String targetAccount = "10001234" + String.format("%04d", random.nextInt(10000));

                String transactionName;
                if (type == TransactionType.TRANSFER) {
                    if (random.nextBoolean()) {
                        transactionName = getRandomName();
                    } else {
                        transactionName = getRandomShop();
                    }
                } else { // DEPOSIT
                    transactionName = getRandomName();
                }

                TransactionHistory history = TransactionHistory.builder()
                        .account(account)
                        .amount(amount)
                        .transactionName(transactionName)
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
