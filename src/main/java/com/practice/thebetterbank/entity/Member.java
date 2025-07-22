package com.practice.thebetterbank.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Member")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    @Builder
    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
