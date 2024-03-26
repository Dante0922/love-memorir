package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lovememoir.server.domain.diarypage.QDiaryPage.diaryPage;

@Repository
@Slf4j
public class DiaryPageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DiaryPageQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<DiaryPagesResponse> findByDiaryId(final Long diaryId, Pageable pageable) {
        List<Long> ids = queryFactory
            .select(diaryPage.id)
            .from(diaryPage)
            .where(
                diaryPage.isDeleted.isFalse(),
                diaryPage.diary.id.eq(diaryId)
            )
            .orderBy(diaryPage.createdDateTime.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new SliceImpl<>(new ArrayList<>(), pageable, false);
        }

        boolean hasNext = false;
        if (ids.size() > pageable.getPageSize()) {
            ids.remove(pageable.getPageSize());
            hasNext = true;
        }

        List<DiaryPagesResponse> content = queryFactory
            .select(
                Projections.constructor(
                    DiaryPagesResponse.class,
                    diaryPage.id,
                    diaryPage.title
                )
            )
            .from(diaryPage)
            .where(
                diaryPage.id.in(ids)
            )
            .orderBy(diaryPage.createdDateTime.desc())
            .fetch();

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
