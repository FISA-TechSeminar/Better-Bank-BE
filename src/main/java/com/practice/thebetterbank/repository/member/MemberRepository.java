package com.practice.thebetterbank.repository.member;

import com.practice.thebetterbank.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getUserById(Long id);
}
