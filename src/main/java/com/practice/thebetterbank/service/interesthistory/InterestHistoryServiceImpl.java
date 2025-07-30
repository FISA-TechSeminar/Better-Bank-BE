package com.practice.thebetterbank.service.interesthistory;

import com.practice.thebetterbank.controller.dto.InterestDTO;
import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.InterestHistory;
import com.practice.thebetterbank.repository.interesthistory.InterestHistoryRepository;
import com.practice.thebetterbank.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterestHistoryServiceImpl implements InterestHistoryService {

    private final InterestHistoryRepository interestHistoryRepository;

    private static final String INTEREST_CACHE_PREFIX = "interest:calc:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final AccountService accountService;

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

    public InterestDTO getCachedInterest(Long accountId) {
        String key = INTEREST_CACHE_PREFIX + accountId;

        // 1. Redis 조회
        InterestDTO cached = (InterestDTO) redisTemplate.opsForValue().get(key);
        if (cached != null) return cached;

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

            // 3. Redis에 저장 (Write-Through)
            redisTemplate.opsForValue().set(key, interestDTO);

            return interestDTO;
        }

        return null;
    }

    public void evictCachedInterest(Long accountId) {
        redisTemplate.delete(INTEREST_CACHE_PREFIX + accountId);
    }
}
