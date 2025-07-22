package com.practice.thebetterbank.service.user;

import com.practice.thebetterbank.entity.Member;

import java.util.Optional;

public interface MemberService {
    public Optional<Member> getUserById(Long id);

    public void save(Member user);
}
