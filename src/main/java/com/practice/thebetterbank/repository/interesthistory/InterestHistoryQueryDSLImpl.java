package com.practice.thebetterbank.repository.interesthistory;

import com.practice.thebetterbank.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class InterestHistoryQueryDSLImpl implements InterestHistoryQueryDSL {

    private final JPAQueryFactory queryFactory;

    // 오늘 이자 지금 받기를 받은적 있는지
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

    // 마지막 이자 지금 받기를 받은 날짜
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

    // 오늘 모든 트랜잭션 값의 합
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