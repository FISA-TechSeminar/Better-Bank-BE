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
        Member member = new Member();
    }

}
