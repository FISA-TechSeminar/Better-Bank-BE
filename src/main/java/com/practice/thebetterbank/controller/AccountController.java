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

        LocalDate today = LocalDate.now();

        boolean todayInterest = interestHistoryService.getExistsTodayInterest(accountId,today);

        if (todayInterest) {
        // 이미 오늘 수령한 경우
            return  ResultDTO.res(HttpStatus.BAD_REQUEST,"오늘은 이미 수령하셨습니다");

        }

        LocalDate gotInterestDate = interestHistoryService.getLastInterestDate(accountId);

        int daysBetween = (int) ChronoUnit.DAYS.between(gotInterestDate, today);

        Optional<Account> foundAccount= accountService.getAccountById(accountId);

        Long todayTransactions = interestHistoryService.getBalanceExcludingTodayTransactions(accountId,today);

        if (foundAccount.isPresent()) {
            long interest = (long) (daysBetween
                    * foundAccount.get().getInterestRate() / 100
                    * (foundAccount.get().getBalance() - todayTransactions))/365;

            interestHistoryService.saveInterest(foundAccount.get(), interest, today);
            interestHistoryService.evictCachedInterest(accountId);

            transactionHistoryService.receiveInterest(foundAccount.get(),interest, today, "183-917375-18402");

            // 새 계좌 잔액
            Long newBalance = foundAccount.get().getBalance() + interest;

            foundAccount.get().increaseBalance(interest);
            accountService.save(foundAccount.get());

            ReceiveInterestDTO receiveInterestDTO = ReceiveInterestDTO.builder().accountId(accountId).interestAmount(interest).newBalance(newBalance).receivedDate(today).build();
            return ResultDTO.res(HttpStatus.ACCEPTED,"이자 받기 성공",receiveInterestDTO);
        }
        return ResultDTO.res(HttpStatus.BAD_REQUEST,"이자 조회 실패");

    }
}
