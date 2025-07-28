package com.practice.thebetterbank.repository.interesthistory;

import com.practice.thebetterbank.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class InterestHistoryQueryDSLImpl implements InterestHistoryQueryDSL {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsTodayInterest(Long accountId, LocalDate today) {
        QInterestHistory ih = QInterestHistory.interestHistory;

        Integer fetchOne = queryFactory
                .selectOne()
                .from(ih)
                .where(
                        ih.account.id.eq(accountId),
                        ih.ihDate.eq(today)
                )
                .fetchFirst(); // 하나만 조회

        return fetchOne != null;
    }

    @Override
    public LocalDate findLastInterestDate(Long accountId) {
        QInterestHistory ih = QInterestHistory.interestHistory;

        return queryFactory
                .select(ih.ihDate)
                .from(ih)
                .where(ih.account.id.eq(accountId))
                .orderBy(ih.ihDate.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Long findBalanceTodayTransactions(Long accountId, LocalDate today) {
        QTransactionHistory th = QTransactionHistory.transactionHistory;

        // 오늘 발생한 거래 총합
        Long todayTransactionSum = queryFactory
                .select(th.amount.sum())
                .from(th)
                .where(
                        th.account.id.eq(accountId),
                        th.transactionDate.eq(today)
                )
                .fetchOne();

        if (todayTransactionSum == null) todayTransactionSum = 0L;

        return todayTransactionSum;
    }
}