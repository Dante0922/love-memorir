package com.lovememoir.server.domain.auth.repository;

import com.lovememoir.server.domain.auth.Auth;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.lovememoir.server.domain.auth.QAuth.auth;


@Repository
@RequiredArgsConstructor
public class AuthQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public Auth findByProviderId(String id) {
        if (id == null) {
            return null;
        }

        return jpaQueryFactory
            .select(auth)
            .from(auth)
            .where(auth.providerId.eq(id))
            .fetchOne();
    }

}
