package com.lovememoir.server.domain.member.repository;

import com.lovememoir.server.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.lovememoir.server.domain.auth.QAuth.auth;


@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public Member findByProviderId(String id) {
        if (id == null) {
            return null;
        }
        return jpaQueryFactory
            .select(auth.member)
            .from(auth)
            .where(auth.providerId.eq(id))
            .fetchOne();
    }
}
