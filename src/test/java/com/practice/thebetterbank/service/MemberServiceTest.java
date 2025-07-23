package com.practice.thebetterbank.service;


import com.practice.thebetterbank.entity.Member;
import com.practice.thebetterbank.service.user.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void saveUserTest() {
        Member[] users = new Member[] { Member.builder().username("이조은").build(),
                Member.builder().username("서민지").build(),
                Member.builder().username("이영주").build(),
                Member.builder().username("홍윤기").build()};
        for(Member user : users) {
            memberService.save(user);
        }
    }

}
