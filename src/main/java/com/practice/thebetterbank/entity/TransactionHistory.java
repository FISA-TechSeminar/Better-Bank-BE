package com.practice.thebetterbank.entity;

import com.practice.thebetterbank.entity.type.TransactionType;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_history_seq")
    @SequenceGenerator(name = "transaction_history_seq", sequenceName = "transaction_history_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private Long amount;

    @Column(name = "transaction_name")
    private String transactionName;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "target_account_number")
    private String targetAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Builder
    public TransactionHistory(Long id, Account account, Long amount, String transactionName,
                              LocalDate transactionDate, String targetAccountNumber, TransactionType transactionType) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.transactionName = transactionName;
        this.transactionDate = transactionDate;
        this.targetAccountNumber = targetAccountNumber;
        this.transactionType = transactionType;
    }
}
