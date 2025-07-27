package com.practice.thebetterbank.repository.interesthistory;

import java.time.LocalDate;

public interface InterestHistoryQueryDSL {

    // 오늘 이미 이자 받은 적 있는지 여부 확인
    boolean existsTodayInterest(Long accountId, LocalDate today);

    // 가장 최근 이자 수령일
    LocalDate findLastInterestDate(Long accountId);

    // 가장 최근의 원금 (이자 계산용)
    Long findBalanceExcludingTodayTransactions(Long accountId, LocalDate today);
}