package com.practice.thebetterbank.controller.dto;

import com.practice.thebetterbank.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private String name;
    private Long balance;
    private Double interestRate;
    private Long userId;

    @Builder
    public AccountDTO(Long balance, Long id, String accountNumber, String name, Double interestRate, Long userId) {
        this.balance = balance;
        this.id = id;
        this.accountNumber = accountNumber;
        this.name = name;
        this.interestRate = interestRate;
        this.userId = userId;
    }

    // Account.java 내부
    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }
        return AccountDTO.builder().id(account.getId())
                .accountNumber(account.getAccountNumber())
                .name(account.getName())
                .balance(account.getBalance())
                .interestRate(account.getInterestRate())
                .userId(account.getMember().getId())
                .build();
    }

}

