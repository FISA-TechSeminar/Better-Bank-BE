package com.practice.thebetterbank.service;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.repository.interesthistory.InterestHistoryRepository;
import com.practice.thebetterbank.service.account.AccountService;
import com.practice.thebetterbank.service.interesthistory.InterestHistoryService;
import com.practice.thebetterbank.service.transactionhistory.TransactionHistoryService;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Setter
@SpringBootTest
@Slf4j
public class InterestHistoryServiceTest {
    @Autowired
    private InterestHistoryService interestHistoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private InterestHistoryRepository interestHistoryRepository;


    @Test
    public void insertInterestHistoryLikeReceiveAPI() {
        LocalDate today = LocalDate.of(2025, 7, 27);
        Random random = new Random();

        for (long accountId = 1; accountId <= 12; accountId++) {
            Optional<Account> foundAccountOpt = accountService.getAccountById(accountId);
            if (foundAccountOpt.isEmpty()) continue;
            Account account = foundAccountOpt.get();

            // 이자 내역이 없다 가정하므로 초기 지급일을 랜덤 설정
            LocalDate baseDate = LocalDate.of(2023, 1, 1).plusDays(random.nextInt(300)); // 시작일
            long balance = account.getBalance();
            double interestRate = account.getInterestRate();

            for (int i = 0; i < 5; i++) {
                // 다음 지급일을 이전 지급일보다 나중으로 설정
                long daysBetween = ChronoUnit.DAYS.between(baseDate.plusDays(1), today);
                if (daysBetween <= 0) break;
                LocalDate randomDate = baseDate.plusDays(1 + random.nextInt((int) daysBetween));

                int interestDays = (int) ChronoUnit.DAYS.between(baseDate, randomDate) - 1;
                if (interestDays <= 0) continue;

                // 오늘 발생한 거래 제외 (처음이니 0으로)
                long todayTransactionAmount = 0L;

                long interestAmount = (long) (interestDays * interestRate * (balance - todayTransactionAmount) / 365);

                // 이자 지급
                interestHistoryService.saveInterest(account, interestAmount, randomDate);
                transactionHistoryService.receiveInterest(account, interestAmount, randomDate, "183-917375-18402");

                // 잔액 증가
                account.increaseBalance(interestAmount);
                accountService.save(account);
                balance = account.getBalance(); // 업데이트된 잔액 사용

                // 다음 루프를 위한 최신 지급일 갱신
                baseDate = randomDate;
            }
        }
    }


}
