package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.controller.dto.ResultDTO;
import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResultDTO<List<Account>> getAccountsByMemberId(@PathVariable Long memberId) {
        List<Account> accounts = accountService.getAccountsByMemberId(memberId);

        return ResultDTO.res(HttpStatus.OK, "계좌 목록 조회 성공", accounts);
    }

    // 계좌 상세 조회
    @GetMapping("/accounts/{accountId}")
    public ResultDTO<Account> getAccountById(@PathVariable Long accountId) {

        return accountService.getAccountById(accountId)
                .map(account -> ResultDTO.res(HttpStatus.OK, "계좌 상세 조회 성공", account))
                .orElse(ResultDTO.res(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }
}
