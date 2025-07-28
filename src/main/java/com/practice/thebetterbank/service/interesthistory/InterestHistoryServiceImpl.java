package com.practice.thebetterbank.service.interesthistory;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.InterestHistory;
import com.practice.thebetterbank.repository.interesthistory.InterestHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InterestHistoryServiceImpl implements InterestHistoryService {

    private final InterestHistoryRepository interestHistoryRepository;

    @Override
    public Boolean getExistsTodayInterest(Long accountId, LocalDate today) {
        return interestHistoryRepository.existsTodayInterest(accountId, today);
    }

    @Override
    public LocalDate getLastInterestDate(Long accountId) {
        return interestHistoryRepository.findLastInterestDate(accountId);
    }

    @Override
    public Long getBalanceExcludingTodayTransactions(Long accountId, LocalDate today){
        return interestHistoryRepository.findBalanceTodayTransactions(accountId, today);
    }

    @Override
    public void saveInterest(Account account, double interest, LocalDate today) {
        interestHistoryRepository.save(InterestHistory.builder()
                .account(account)
                .ihDate(today)
                .ihAmount(interest)
                .build());
    }

    ;
}
