package com.practice.thebetterbank.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class InterestDTO {
    private String accountId;
    private LocalDate lastInterestDate;
    private Long interestAmount;
}
