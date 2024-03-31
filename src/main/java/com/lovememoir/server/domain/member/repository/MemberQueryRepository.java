package com.lovememoir.server.domain.member.repository;

import com.lovememoir.server.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.lovememoir.server.domain.OAuth.QOAuth.oAuth;
import static com.lovememoir.server.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public Member findByProviderId(String ProviderId) {
        return jpaQueryFactory
            .select(oAuth.member)
            .from(oAuth)
            .where(oAuth.providerId.eq(ProviderId))
            .fetchOne();
    }
}
