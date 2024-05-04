package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageDto;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.lovememoir.server.domain.diarypage.QDiaryPage.diaryPage;

@Repository
@Slf4j
public class DiaryPageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DiaryPageQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Long> findAllIdByDiaryId(final Long diaryId, Pageable pageable) {
        return queryFactory
            .select(diaryPage.id)
            .from(diaryPage)
            .where(
                diaryPage.isDeleted.isFalse(),
                diaryPage.diary.id.eq(diaryId)
            )
            .orderBy(
                diaryPage.recordDate.desc(),
                diaryPage.createdDateTime.desc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();
    }

    public List<DiaryPagesResponse> findAllByDiaryIdIn(final List<Long> diaryIds) {
        return queryFactory
            .select(
                Projections.fields(
                    DiaryPagesResponse.class,
                    diaryPage.id.as("diaryPageId"),
                    diaryPage.analysis.analysisStatus,
                    diaryPage.analysis.emotionCode,
                    diaryPage.title,
                    diaryPage.createdDateTime
                )
            )
            .from(diaryPage)
            .where(diaryPage.id.in(diaryIds))
            .orderBy(
                diaryPage.recordDate.desc(),
                diaryPage.createdDateTime.desc()
            )
            .fetch();
    }

    public Optional<DiaryPageDto> findById(final long diaryPageId) {
        DiaryPageDto content = queryFactory
            .select(
                Projections.fields(
                    DiaryPageDto.class,
                    Expressions.asNumber(diaryPageId).as("diaryPageId"),
                    diaryPage.analysis.analysisStatus,
                    diaryPage.analysis.emotionCode,
                    diaryPage.title,
                    diaryPage.content,
                    diaryPage.recordDate,
                    diaryPage.createdDateTime
                )
            )
            .from(diaryPage)
            .where(diaryPage.id.eq(diaryPageId))
            .fetchFirst();
        return Optional.ofNullable(content);
    }

    public int countAllByDiaryId(final long diaryId) {
        return queryFactory
            .select(diaryPage.id)
            .from(diaryPage)
            .where(
                diaryPage.isDeleted.isFalse(),
                diaryPage.diary.id.eq(diaryId)
            )
            .fetch()
            .size();
    }
}
