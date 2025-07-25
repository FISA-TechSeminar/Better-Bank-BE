//package com.practice.thebetterbank.repository;
//
//import com.practice.thebetterbank.repository.interesthistory.InterestHistoryRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Transactional
//public class InterestHistoryQueryDSLImplTest {
//
//    @Autowired
//    private InterestHistoryRepository interestHistoryRepository;
//
//    @Test
//    public void testFindLastInterestDate() {
//        Long testAccountId = 1L;
//
//        // given - 테스트 데이터 준비
//        // 필요하면 interestHistoryRepository.save(...)로 데이터 추가
//
//        // when
//        LocalDate lastInterestDate = interestHistoryRepository.findLastInterestDate(testAccountId);
//
//        // then
//        assertNotNull(lastInterestDate);
//        System.out.println("마지막 이자 수령일: " + lastInterestDate);
//    }
//
//    @Test
//    public void testExistsTodayInterest() {
//        Long testAccountId = 1L;
//        LocalDate today = LocalDate.now();
//
//        boolean exists = interestHistoryRepository.existsTodayInterest(testAccountId, today);
//
//        assertFalse(exists);
//    }
//
//    @Test
//    public void testFindLatestPrincipalAmount() {
//        Long testAccountId = 1L;
//
//        Long amount = interestHistoryRepository.findLatestPrincipalAmount(testAccountId);
//
//        assertNotNull(amount);
//        System.out.println("최신 원금: " + amount);
//    }
//}
