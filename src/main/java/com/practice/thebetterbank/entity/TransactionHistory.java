package com.practice.thebetterbank.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Transaction_History")
@Getter
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK to Account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private Integer amount;

    @Column(name = "transaction_name")
    private String transactionName;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "target_account_number")
    private String targetAccountNumber;

    @Column(name = "transaction_type")
    private String transactionType;

    @Builder
    public TransactionHistory(Long id, Account account, Integer amount, String transactionName, LocalDate transactionDate, String targetAccountNumber, String transactionType) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.transactionName = transactionName;
        this.transactionDate = transactionDate;
        this.targetAccountNumber = targetAccountNumber;
        this.transactionType = transactionType;
    }
}
