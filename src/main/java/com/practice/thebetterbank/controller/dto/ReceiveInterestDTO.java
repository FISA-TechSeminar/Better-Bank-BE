package com.practice.thebetterbank.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ReceiveInterestDTO {
    private Long accountId;
    private Long interestAmount;
    private Long newBalance;
    private LocalDate receivedDate;
}
