package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public class DiaryPageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DiaryPageQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<DiaryPagesResponse> findByDiaryId(final Long diaryId, Pageable pageable) {
        return null;
    }
}
