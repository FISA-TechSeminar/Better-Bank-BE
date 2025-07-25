package com.practice.thebetterbank.service.user;

import com.practice.thebetterbank.entity.Member;
import com.practice.thebetterbank.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> getUserById(Long id) {
        return Optional.ofNullable(memberRepository.getUserById(id));
    }

    @Override
    public void save(Member user) {
        memberRepository.save(user);
    }
}
