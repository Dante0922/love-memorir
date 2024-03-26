package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiaryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public DiaryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<DiarySearchResponse> findByMemberKey(String memberKey) {
        return null;
    }
}
