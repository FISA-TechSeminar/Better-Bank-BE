package com.practice.thebetterbank.service.interesthistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.thebetterbank.controller.dto.InterestDTO;
import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.InterestHistory;
import com.practice.thebetterbank.repository.interesthistory.InterestHistoryRepository;
import com.practice.thebetterbank.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class InterestHistoryServiceImpl implements InterestHistoryService {

    private final InterestHistoryRepository interestHistoryRepository;

    private static final String INTEREST_CACHE_PREFIX = "interest:calc:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final AccountService accountService;

    public InterestDTO receiveInterest(Long accountId) {
        LocalDate today = LocalDate.now();
        String lockKey = "interest:lock:" + accountId;

        // 1. 분산락 획득 시도 (5초 TTL)
        Boolean lockAcquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "LOCK", 5, TimeUnit.SECONDS);

        if (lockAcquired == null || !lockAcquired) {
            // 락 획득 실패 시: 동시에 처리 중이므로 중복 방지
            return null;
        }

        try {
            // 2. 오늘 이자 수령 여부 확인
            if (getExistsTodayInterest(accountId, today)) {
                return null;
            }

            // 3. 계좌 조회
            Optional<Account> foundAccount = accountService.getAccountById(accountId);
            if (foundAccount.isEmpty()) return null;

            Account account = foundAccount.get();

            // 4. 이자 계산
            LocalDate gotInterestDate = getLastInterestDate(accountId);
            int daysBetween = (int) ChronoUnit.DAYS.between(gotInterestDate, today);
            Long todayTransactions = getBalanceExcludingTodayTransactions(accountId, today);

            double interest = daysBetween
                    * account.getInterestRate() / 100
                    * (account.getBalance() - todayTransactions);

            // 5. DB 저장 + 캐시 갱신
            saveInterest(account, interest, today);

            // Redis 캐시를 0원으로 갱신
            InterestDTO zeroInterest = InterestDTO.builder()
                    .accountId(accountId)
                    .lastInterestDate(today)
                    .interestAmount(0L)
                    .build();

            redisTemplate.opsForValue().set(
                    "interest:calc:" + accountId,
                    zeroInterest,
                    getSecondsUntilMidnight(), // 오늘 밤까지 유효
                    TimeUnit.SECONDS
            );

            // 6. 응답 DTO 반환
            return InterestDTO.builder()
                    .accountId(accountId)
                    .lastInterestDate(today)
                    .interestAmount((long) interest)
                    .build();

        } finally {
            // 7. 락 해제
            redisTemplate.delete(lockKey);
        }
    }



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

//    @Override
//    public void saveInterest(Account account, double interest, LocalDate today) {
//        interestHistoryRepository.save(InterestHistory.builder()
//                .account(account)
//                .ihDate(today)
//                .ihAmount(interest)
//                .build());
//    }

    @Override
    public void saveInterest(Account account, double interest, LocalDate today) {
        // 1. DB 저장
        interestHistoryRepository.save(InterestHistory.builder()
                .account(account)
                .ihDate(today)
                .ihAmount(interest)
                .build());

        // 2. Redis 캐시 업데이트
        InterestDTO updatedDto = InterestDTO.builder()
                .accountId(account.getId())
                .lastInterestDate(today)
                .interestAmount((long) interest)
                .build();

        String key = "interest:calc:" + account.getId();
        redisTemplate.opsForValue().set(key, updatedDto, getSecondsUntilMidnight(), TimeUnit.SECONDS);
    }

    @Autowired
    private ObjectMapper objectMapper;
    public InterestDTO getCachedInterest(Long accountId) {
        String key = INTEREST_CACHE_PREFIX + accountId;

        // 1. Redis 조회
        Object cachedObject = redisTemplate.opsForValue().get(key);
        if (cachedObject != null) {
            // 안전하게 LinkedHashMap -> DTO 변환
            InterestDTO cached = objectMapper.convertValue(cachedObject, InterestDTO.class);
            return cached;
        }

        // 2. 캐시에 없으면 계산
        LocalDate today = LocalDate.now();
        if (getExistsTodayInterest(accountId, today)) return null;

        LocalDate gotInterestDate = getLastInterestDate(accountId);
        int daysBetween = (int) ChronoUnit.DAYS.between(gotInterestDate, today);
        Optional<Account> foundAccount = accountService.getAccountById(accountId);
        Long todayTransactions = getBalanceExcludingTodayTransactions(accountId, today);

        if (foundAccount.isPresent()) {
            double interest = daysBetween
                    * foundAccount.get().getInterestRate() / 100
                    * (foundAccount.get().getBalance() - todayTransactions);

            InterestDTO interestDTO = InterestDTO.builder()
                    .accountId(accountId)
                    .lastInterestDate(gotInterestDate)
                    .interestAmount((long) interest)
                    .build();

//            // 3. Redis에 저장 (오늘 자정까지 TTL)
            redisTemplate.opsForValue().set(key, interestDTO, getSecondsUntilMidnight(), TimeUnit.SECONDS);


            return interestDTO;
        }

        return null;
    }

    private long getSecondsUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        return Duration.between(now, midnight).getSeconds();
    }


    public void evictCachedInterest(Long accountId) {
        redisTemplate.delete(INTEREST_CACHE_PREFIX + accountId);
    }
}
