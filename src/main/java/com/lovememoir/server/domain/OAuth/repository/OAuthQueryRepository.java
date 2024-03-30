package com.lovememoir.server.domain.OAuth.repository;

import com.lovememoir.server.domain.OAuth.OAuth;
import com.lovememoir.server.domain.OAuth.QOAuth;
import com.lovememoir.server.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.lovememoir.server.domain.OAuth.QOAuth.oAuth;
import static com.lovememoir.server.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class OAuthQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public OAuth findByProviderId(String providerId) {
        return jpaQueryFactory
            .selectFrom(oAuth)
            .where(oAuth.providerId.eq(providerId))
            .fetchOne();
    }
}
