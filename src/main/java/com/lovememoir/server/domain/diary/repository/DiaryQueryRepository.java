package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.domain.diary.QDiary;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.lovememoir.server.domain.member.QMember;
import com.querydsl.core.types.Projections;
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

    public List<DiarySearchResponse> findByMemberKey(final String memberKey) {
        return queryFactory
            .select(
                Projections.constructor(
                    DiarySearchResponse.class,
                    diary.id,
                    diary.isFixed,
                    diary.title,
                    diary.pageCount,
                    diary.relationshipStartedDate
                )
            )
            .from(diary)
            .join(diary.member, member)
            .where(
                diary.isDeleted.isFalse(),
                diary.member.memberKey.eq(memberKey)
            )
            .orderBy(diary.createdDateTime.desc())
            .fetch();
    }

    public List<DiarySearchResponse> findMainDiaries(final String memberKey) {
        return queryFactory
            .select(
                Projections.constructor(
                    DiarySearchResponse.class,
                    diary.id,
                    diary.isFixed,
                    diary.title,
                    diary.pageCount,
                    diary.relationshipStartedDate
                )
            )
            .from(diary)
            .join(diary.member, member)
            .where(
                diary.isDeleted.isFalse(),
                diary.member.memberKey.eq(memberKey),
                diary.isFixed.isTrue()
            )
            .orderBy(diary.createdDateTime.desc())
            .fetch();
    }
}
