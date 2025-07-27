package com.practice.thebetterbank.repository.interesthistory;

import com.practice.thebetterbank.entity.InterestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestHistoryRepository extends JpaRepository<InterestHistory, Long>, InterestHistoryQueryDSL {

}