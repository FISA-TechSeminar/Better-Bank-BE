package com.practice.thebetterbank.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberQueryDSLImpl implements MemberQueryDSL {

    private final JPAQueryFactory query;


}
