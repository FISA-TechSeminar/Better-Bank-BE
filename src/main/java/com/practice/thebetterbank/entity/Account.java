package com.practice.thebetterbank.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Account")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    private Long balance;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<InterestHistory> interestHistories = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TransactionHistory> transactionHistories = new ArrayList<>();

    @Builder
    public Account(Long id, Member member, String name, Long balance, Double interestRate, String accountNumber, List<InterestHistory> interestHistories, List<TransactionHistory> transactionHistories) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.balance = balance;
        this.interestRate = interestRate;
        this.accountNumber = accountNumber;
        this.interestHistories = interestHistories;
        this.transactionHistories = transactionHistories;
    }
}
