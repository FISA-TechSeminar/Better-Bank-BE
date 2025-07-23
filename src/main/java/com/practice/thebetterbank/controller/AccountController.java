package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // 유저별 계좌 목록 조회
    @GetMapping("/members/{memberId}/accounts")
    public ResponseEntity<List<Account>> getAccountsByMemberId(@PathVariable Long memberId) {
        List<Account> accounts = accountService.getAccountsByMemberId(memberId);
        return ResponseEntity.ok(accounts);
    }

    // 계좌 상세 조회
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        return accountService.getAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
