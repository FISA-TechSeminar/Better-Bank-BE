package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.controller.dto.ResultDTO;
import com.practice.thebetterbank.controller.dto.TransactionHistoryListDTO;
import com.practice.thebetterbank.entity.TransactionHistory;
import com.practice.thebetterbank.service.account.AccountService;
import com.practice.thebetterbank.service.transactionhistory.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transactionhistory")
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResultDTO<TransactionHistoryListDTO> getTransactionHistoryByAccountId(
            @PathVariable("accountId") Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if(accountId == null) {
            return new ResultDTO<>(HttpStatus.BAD_REQUEST, "accountId is null");
        }
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "transactionDate"));
            Page<TransactionHistory> pageResult = transactionHistoryService.getTransactionHistoryByAccountId(accountId, pageable);
            Long total = accountService.getAccountById(accountId).get().getBalance();
            TransactionHistoryListDTO result= TransactionHistoryListDTO.builder().transactionHistories(pageResult).balance(total).build();

            return ResultDTO.res(HttpStatus.OK, "계좌 거래내역을 불러왔습니다!", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.res(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 생겼습니다");
        }
    }

}
