package com.practice.thebetterbank.repository.account;

import com.practice.thebetterbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByMemberId(Long memberId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
