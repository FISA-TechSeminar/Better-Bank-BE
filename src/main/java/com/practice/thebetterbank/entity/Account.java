// Account.java
package com.practice.thebetterbank.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    private String name;

    private Long balance;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InterestHistory> interestHistories = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TransactionHistory> transactionHistories = new ArrayList<>();

    @Builder
    public Account(Long id, Member member, String name, Long balance, Double interestRate, String accountNumber,
                   List<InterestHistory> interestHistories, List<TransactionHistory> transactionHistories) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.balance = balance;
        this.interestRate = interestRate;
        this.accountNumber = accountNumber;
        this.interestHistories = interestHistories != null ? interestHistories : new ArrayList<>();
        this.transactionHistories = transactionHistories != null ? transactionHistories : new ArrayList<>();
    }

    // 잔액 증가
    public void increaseBalance(Long amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("증가할 금액은 0보다 커야 합니다.");
        }
        this.balance += amount;
    }

    // 잔액 감소
    public void decreaseBalance(Long amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("차감할 금액은 0보다 커야 합니다.");
        }
        if (this.balance < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.balance -= amount;
    }

}
