package com.practice.thebetterbank.service.interesthistory;

import com.practice.thebetterbank.controller.dto.InterestDTO;
import com.practice.thebetterbank.entity.Account;

import java.time.LocalDate;

public interface InterestHistoryService {

    Boolean getExistsTodayInterest(Long accountId, LocalDate today);
    LocalDate getLastInterestDate(Long accountId);
    Long getBalanceExcludingTodayTransactions(Long accountId, LocalDate today);

    void saveInterest(Account account, double interest, LocalDate today);

    //redis
    InterestDTO getCachedInterest(Long accountId);

    void evictCachedInterest(Long accountId);
}
