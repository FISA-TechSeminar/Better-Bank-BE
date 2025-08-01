package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.controller.dto.AccountDTO;
import com.practice.thebetterbank.controller.dto.InterestDTO;
import com.practice.thebetterbank.controller.dto.ReceiveInterestDTO;
import com.practice.thebetterbank.controller.dto.ResultDTO;
import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.repository.transactionhistory.TransactionHistoryRepository;
import com.practice.thebetterbank.service.account.AccountService;
import com.practice.thebetterbank.service.interesthistory.InterestHistoryService;
import com.practice.thebetterbank.service.transactionhistory.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.practice.thebetterbank.entity.QTransactionHistory.transactionHistory;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final InterestHistoryService interestHistoryService;

    private final TransactionHistoryService transactionHistoryService;

    // 유저별 계좌 목록 조회
    @GetMapping("/members/{memberId}/accounts")
    public ResultDTO<List<AccountDTO>> getAccountsByMemberId(@PathVariable("memberId") Long memberId) {
        List<AccountDTO> accounts = accountService.getAccountsByMemberId(memberId)
                        .stream().map(AccountDTO::toDTO)
                        .toList();

        return ResultDTO.res(HttpStatus.OK, "계좌 목록 조회 성공", accounts);
    }

    // 계좌 상세 조회
    @GetMapping("/accounts/{accountId}")
    public ResultDTO<Account> getAccountById(@PathVariable("accountId") Long accountId) {

        return accountService.getAccountById(accountId)
                .map(account -> ResultDTO.res(HttpStatus.OK, "계좌 상세 조회 성공", account))
                .orElse(ResultDTO.res(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }

    @GetMapping("/accounts/{accountId}/interest")
    public ResultDTO<InterestDTO> selectInterestByAccountId(@PathVariable("accountId") Long accountId) {
        InterestDTO interestDTO = interestHistoryService.getCachedInterest(accountId);

        if (interestDTO == null) {
            return ResultDTO.res(HttpStatus.BAD_REQUEST, "오늘은 이미 수령하셨습니다");
        }

        return ResultDTO.res(HttpStatus.ACCEPTED, "이자 조회 성공", interestDTO);
    }



    @GetMapping("/accounts/{accountId}/receiveinterest")
    public ResultDTO<ReceiveInterestDTO> receiveInterestByAccountId(@PathVariable("accountId") Long accountId) {
        InterestDTO dto = interestHistoryService.receiveInterest(accountId);

        if (dto == null || dto.getInterestAmount() == 0L) {
            return ResultDTO.res(HttpStatus.BAD_REQUEST, "오늘은 이미 수령하셨습니다");
        }

        // 계좌 잔액 증가
        Optional<Account> foundAccount = accountService.getAccountById(accountId);
        if (foundAccount.isEmpty()) return ResultDTO.res(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없습니다");
        Account account = foundAccount.get();
        account.increaseBalance(dto.getInterestAmount());
        accountService.save(account);

        // 거래내역 저장
        transactionHistoryService.receiveInterest(account, dto.getInterestAmount(), dto.getLastInterestDate(), "183-917375-18402");

        // 응답 DTO 생성
        ReceiveInterestDTO result = ReceiveInterestDTO.builder()
                .accountId(accountId)
                .interestAmount(dto.getInterestAmount())
                .newBalance(account.getBalance())
                .receivedDate(dto.getLastInterestDate())
                .build();

        return ResultDTO.res(HttpStatus.ACCEPTED, "이자 받기 성공", result);
    }
}
