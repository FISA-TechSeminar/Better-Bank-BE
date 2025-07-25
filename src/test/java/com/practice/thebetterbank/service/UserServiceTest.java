package com.practice.thebetterbank.service;


import com.practice.thebetterbank.entity.Member;
import com.practice.thebetterbank.service.user.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void saveMemberTest() {
        Member[] members = new Member[] { Member.builder().id(1L).username("이조은").build(),
        Member.builder().id(2L).username("서민지").build(),
        Member.builder().id(3L).username("이영주").build(),
        Member.builder().id(4L).username("홍윤기").build()};
        for(Member member : members) {
            memberService.save(member);
        }

    }

}
