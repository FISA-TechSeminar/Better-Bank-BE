package com.practice.thebetterbank.repository;

import com.practice.thebetterbank.repository.interesthistory.InterestHistoryQueryDSLImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.practice.thebetterbank.entity.QInterestHistory;
import com.practice.thebetterbank.entity.QTransactionHistory;
import com.practice.thebetterbank.entity.QAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InterestHistoryQueryDSLImplTest {

    private JPAQueryFactory queryFactory;
    private InterestHistoryQueryDSLImpl interestHistoryQueryDSL;

    @BeforeEach
    void setUp() {
        queryFactory = mock(JPAQueryFactory.class);
        interestHistoryQueryDSL = new InterestHistoryQueryDSLImpl(queryFactory);
    }

    @Test
    void testExistsTodayInterest() {
        JPAQuery<Integer> jpaQuery = mock(JPAQuery.class);
        QInterestHistory ih = QInterestHistory.interestHistory;
        LocalDate today = LocalDate.now();

        // 모킹 체인 설정
        when(queryFactory.selectOne()).thenReturn(jpaQuery);
        when(jpaQuery.from(ih)).thenReturn(jpaQuery);
        when(jpaQuery.where(ih.account.id.eq(1L), ih.ihDate.eq(today))).thenReturn(jpaQuery);

        // fetchFirst()가 null 반환 → false 기대
        when(jpaQuery.fetchFirst()).thenReturn(null);
        boolean result = interestHistoryQueryDSL.existsTodayInterest(1L, today);
        assertThat(result).isFalse();

        // fetchFirst()가 1 반환 → true 기대
        when(jpaQuery.fetchFirst()).thenReturn(1);
        result = interestHistoryQueryDSL.existsTodayInterest(1L, today);
        assertThat(result).isTrue();
    }

    @Test
    void testFindLastInterestDate() {
        JPAQuery<LocalDate> jpaQuery = mock(JPAQuery.class);
        QInterestHistory ih = QInterestHistory.interestHistory;
        LocalDate expectedDate = LocalDate.of(2025, 7, 27);

        when(queryFactory.select(ih.ihDate)).thenReturn(jpaQuery);
        when(jpaQuery.from(ih)).thenReturn(jpaQuery);
        when(jpaQuery.where(ih.account.id.eq(1L))).thenReturn(jpaQuery);
        when(jpaQuery.orderBy(ih.ihDate.desc())).thenReturn(jpaQuery);
        when(jpaQuery.limit(1)).thenReturn(jpaQuery);
        when(jpaQuery.fetchOne()).thenReturn(expectedDate);

        LocalDate actualDate = interestHistoryQueryDSL.findLastInterestDate(1L);
        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    void testFindBalanceExcludingTodayTransactions() {
        JPAQuery<Long> transactionQuery = mock(JPAQuery.class);
        JPAQuery<Long> balanceQuery = mock(JPAQuery.class);
        QTransactionHistory th = QTransactionHistory.transactionHistory;
        QAccount account = QAccount.account;
        LocalDate today = LocalDate.now();

        when(queryFactory.select(th.amount.sum())).thenReturn(transactionQuery);
        when(transactionQuery.from(th)).thenReturn(transactionQuery);
        when(transactionQuery.where(th.account.id.eq(1L), th.transactionDate.eq(today))).thenReturn(transactionQuery);
        when(transactionQuery.fetchOne()).thenReturn(200L); // 오늘 거래 합계

        when(queryFactory.select(account.balance)).thenReturn(balanceQuery);
        when(balanceQuery.from(account)).thenReturn(balanceQuery);
        when(balanceQuery.where(account.id.eq(1L))).thenReturn(balanceQuery);
        when(balanceQuery.fetchOne()).thenReturn(1000L); // 현재 잔액

        Long result = interestHistoryQueryDSL.findBalanceTodayTransactions(1L, today);
        assertThat(result).isEqualTo(800L); // 1000 - 200 = 800

        // Null 테스트 (null → 0 처리)
        when(transactionQuery.fetchOne()).thenReturn(null);
        when(balanceQuery.fetchOne()).thenReturn(null);
        result = interestHistoryQueryDSL.findBalanceTodayTransactions(1L, today);
        assertThat(result).isEqualTo(0L);
    }
}
