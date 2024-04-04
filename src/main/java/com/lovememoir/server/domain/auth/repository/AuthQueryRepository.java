package com.lovememoir.server.domain.auth.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class AuthQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
