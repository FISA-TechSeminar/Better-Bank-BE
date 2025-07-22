package com.practice.thebetterbank.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Interest_History")
@Getter
@NoArgsConstructor
public class InterestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate ihDate;

    private Double ihAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public InterestHistory(Long id, LocalDate ihDate, Double ihAmount, Account account, User user) {
        this.id = id;
        this.ihDate = ihDate;
        this.ihAmount = ihAmount;
        this.account = account;
        this.user = user;
    }
}
