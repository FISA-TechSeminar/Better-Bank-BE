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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interest_history_seq")
    @SequenceGenerator(name = "interest_history_seq", sequenceName = "interest_history_seq", allocationSize = 1)
    private Long id;

    @Column(name = "ih_date")
    private LocalDate ihDate;

    @Column(name = "ih_amount")
    private Double ihAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public InterestHistory(Long id, LocalDate ihDate, Double ihAmount, Account account, Member member) {
        this.id = id;
        this.ihDate = ihDate;
        this.ihAmount = ihAmount;
        this.account = account;
        this.member = member;
    }
}
