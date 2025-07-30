package com.practice.thebetterbank.repository.interesthistory;

import com.practice.thebetterbank.entity.InterestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestHistoryRepository extends JpaRepository<InterestHistory, Long>, InterestHistoryQueryDSL {
    Optional<InterestHistory> findTopByAccountIdOrderByIhDateDesc(Long accountId);
}