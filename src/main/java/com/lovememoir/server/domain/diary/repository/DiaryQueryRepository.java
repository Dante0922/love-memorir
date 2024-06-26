package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lovememoir.server.domain.diary.QDiary.diary;

@Repository
public class DiaryQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public DiaryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = em;
    }

    public List<DiarySearchResponse> findAllByMemberId(final long memberId, final boolean isContainsMain) {
        return queryFactory.select(
                Projections.fields(
                    DiarySearchResponse.class,
                    diary.id.as("diaryId"),
                    diary.isMain,
                    diary.title,
                    diary.loveInfo.isLove,
                    diary.loveInfo.startedDate,
                    diary.loveInfo.finishedDate,
                    diary.profile.storeFileUrl.as("profileImage")
                )
            )
            .from(diary)
            .where(
                diary.isDeleted.isFalse(),
                mainIsTrue(isContainsMain),
                diary.isStored.isFalse(),
                diary.member.id.eq(memberId)
            )
            .orderBy(diary.createdDateTime.desc())
            .fetch();
    }

    public List<DiarySearchResponse> findStoreAllByMemberId(final long memberId) {
        return queryFactory.select(
                Projections.fields(
                    DiarySearchResponse.class,
                    diary.id.as("diaryId"),
                    diary.isMain,
                    diary.title,
                    diary.loveInfo.isLove,
                    diary.loveInfo.startedDate,
                    diary.loveInfo.finishedDate,
                    diary.profile.storeFileUrl.as("profileImage")
                )
            )
            .from(diary)
            .where(
                diary.isDeleted.isFalse(),
                diary.isStored.isTrue(),
                diary.member.id.eq(memberId)
            )
            .orderBy(diary.createdDateTime.desc())
            .fetch();
    }

    @Transactional
    public void setAllMainToFalse(Long memberId) {
            queryFactory.update(diary)
                .where(diary.isMain.isTrue(), diary.member.id.eq(memberId), diary.isDeleted.isFalse())
                .set(diary.isMain, false)
                .execute();
    }

    private BooleanExpression mainIsTrue(final boolean isContainsMain) {
        return isContainsMain ? diary.isMain.isTrue() : diary.isMain.isFalse();
    }
}
