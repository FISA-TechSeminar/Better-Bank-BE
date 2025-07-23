package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.controller.dto.ResultDTO;
import com.practice.thebetterbank.entity.TransactionHistory;
import com.practice.thebetterbank.repository.transactionhistory.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionHistoryRepository transactionHistoryRepository;

    @GetMapping("/{accountId}")
    public ResultDTO<List<TransactionHistory>> getTransactionHistoryByAccountId(@PathVariable Long accountId) {
        if(accountId == null) {
            return new ResultDTO<>(HttpStatus.BAD_REQUEST, "accountId is null");
        }
        try {
            return ResultDTO.res(HttpStatus.ACCEPTED,"계좌 거래내역을 불러왔습니다!",transactionHistoryRepository.findAllByAccountId(accountId));
        }catch (Exception e) {
            e.printStackTrace();
            return  ResultDTO.res(HttpStatus.INTERNAL_SERVER_ERROR,"서버에 오류가 생겼습니다");
        }
    }
}
