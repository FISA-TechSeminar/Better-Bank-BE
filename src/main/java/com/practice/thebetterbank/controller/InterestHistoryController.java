package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.controller.dto.InterestDTO;
import com.practice.thebetterbank.service.interesthistory.InterestHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class InterestHistoryController {

    private final InterestHistoryService interestHistoryService;

    @PostMapping("/{accountId}/receiveinterest")
    public ResponseEntity<InterestDTO> receiveInterest(@PathVariable Long accountId) {
        InterestDTO dto = interestHistoryService.receiveInterest(accountId);
        if (dto == null) {
            // 이미 오늘 이자 수령한 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }
        return ResponseEntity.ok(dto);
    }

}
