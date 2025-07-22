package com.practice.thebetterbank.service;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.entity.Member;
import com.practice.thebetterbank.service.account.AccountService;
import com.practice.thebetterbank.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member1;
    private Member member2;
    private Member member3;
    private Member member4;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();

        member1 = memberRepository.save(Member.builder()
                .username("이조은")
                .build());

        member2 = memberRepository.save(Member.builder()
                .username("서민지")
                .build());

        member3 = memberRepository.save(Member.builder()
                .username("이영주")
                .build());

        member4 = memberRepository.save(Member.builder()
                .username("홍윤기")
                .build());
    }

    @Test
    public void saveDummyAccounts() {
        accountService.save(Account.builder().member(member1).name("저축 통장").balance(5000000L).interestRate(1.2).accountNumber("110-1234-5678").build());
        accountService.save(Account.builder().member(member1).name("생활비 통장").balance(1200000L).interestRate(0.5).accountNumber("110-2345-6789").build());
        accountService.save(Account.builder().member(member1).name("월급 통장").balance(30000000L).interestRate(3.0).accountNumber("110-3456-7890").build());

        accountService.save(Account.builder().member(member2).name("월급 통장").balance(800000L).interestRate(1.0).accountNumber("220-1234-5678").build());
        accountService.save(Account.builder().member(member2).name("생활비 통장").balance(1500000L).interestRate(0.6).accountNumber("220-2345-6789").build());
        accountService.save(Account.builder().member(member2).name("비상금 통장").balance(20000000L).interestRate(2.8).accountNumber("220-3456-7890").build());

        accountService.save(Account.builder().member(member3).name("투자 통장").balance(3000000L).interestRate(1.1).accountNumber("330-1234-5678").build());
        accountService.save(Account.builder().member(member3).name("청약 통장").balance(900000L).interestRate(0.4).accountNumber("330-2345-6789").build());
        accountService.save(Account.builder().member(member3).name("용돈 통장").balance(10000000L).interestRate(2.5).accountNumber("330-3456-7890").build());

        accountService.save(Account.builder().member(member4).name("자동이체 통장").balance(4000000L).interestRate(1.3).accountNumber("440-1234-5678").build());
        accountService.save(Account.builder().member(member4).name("여행 통장").balance(1000000L).interestRate(0.45).accountNumber("440-2345-6789").build());
        accountService.save(Account.builder().member(member4).name("생활비 통장").balance(25000000L).interestRate(2.9).accountNumber("440-3456-7890").build());

        log.info("✅ 기존 유저 4명에게 계좌 3개씩 총 12건 저장 완료");
    }
}
