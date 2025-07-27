package com.practice.thebetterbank.service.account;

import com.practice.thebetterbank.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    List<Account> getAllAccounts();
    Optional<Account> getAccountById(Long id);

    // 유저 ID로 계좌 목록 조회 추가
    List<Account> getAccountsByMemberId(Long memberId);

}
