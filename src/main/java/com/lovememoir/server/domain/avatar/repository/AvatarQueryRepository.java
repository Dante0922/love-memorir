package com.lovememoir.server.domain.avatar.repository;

import com.lovememoir.server.domain.avatar.Avatar;
import static com.lovememoir.server.domain.avatar.QAvatar.avatar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;



@Repository
@RequiredArgsConstructor
public class AvatarQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Avatar findByMemberId(Long memberId) {
        return jpaQueryFactory.selectFrom(avatar)
            .where(avatar.member.id.eq(memberId))
            .fetchOne();
    }


    public Optional<Avatar> findByProviderId(String providerId) {
        Avatar result = jpaQueryFactory.selectFrom(avatar)
            .where(avatar.member.auth.providerId.eq(providerId))
            .fetchOne();
        return Optional.ofNullable(result);

    }
}
