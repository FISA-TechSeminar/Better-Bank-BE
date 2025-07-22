package com.practice.thebetterbank.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserQueryDSLImpl implements UserQueryDSL {

    private final JPAQueryFactory query;


}
