package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lovememoir.server.domain.diary.QDiary.diary;
import static com.lovememoir.server.domain.member.QMember.member;

@Repository
public class DiaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DiaryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<DiarySearchResponse> findByMemberKey(final String memberKey, final boolean mainOnly) {
        return queryFactory
            .select(
                Projections.fields(
                    DiarySearchResponse.class,
                    diary.id.as("diaryId"),
                    diary.isFixed.as("isMain"),
                    diary.title,
                    diary.file.storeFileUrl.as("profileImage"),
                    diary.pageCount,
                    diary.relationshipStartedDate
                )
            )
            .from(diary)
            .join(diary.member, member)
            .where(
                diary.isDeleted.isFalse(),
                member.memberKey.eq(memberKey),
                isFixedIsTrue(mainOnly)
            )
            .orderBy(diary.createdDateTime.desc())
            .fetch();
    }

    private BooleanExpression isFixedIsTrue(final boolean mainOnly) {
        return mainOnly ? diary.isFixed.isTrue() : null;
    }
}
