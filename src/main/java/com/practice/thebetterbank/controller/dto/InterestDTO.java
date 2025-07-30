package com.practice.thebetterbank.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestDTO  implements Serializable {
    private Long accountId;
    private LocalDate lastInterestDate;
    private Long interestAmount;
}
