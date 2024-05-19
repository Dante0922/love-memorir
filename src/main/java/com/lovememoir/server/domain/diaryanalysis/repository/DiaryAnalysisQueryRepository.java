package com.lovememoir.server.domain.diaryanalysis.repository;

import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.lovememoir.server.domain.diary.QDiary.diary;
import static com.lovememoir.server.domain.diaryanalysis.QDiaryAnalysis.diaryAnalysis;
import static com.lovememoir.server.domain.diarypage.QDiaryPage.diaryPage;

@Repository
@RequiredArgsConstructor
public class DiaryAnalysisQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<List<DiaryAnalysis>> findTop3RecentAnalysesByMemberId(Long memberId) {
        // 서브쿼리: 각 다이어리마다 가장 높은 weight를 가진 DiaryAnalysis를 선택
        JPAQuery<DiaryAnalysis> subQuery = new JPAQuery<>();
        subQuery.select(diaryAnalysis)
            .from(diaryAnalysis)
            .leftJoin(diaryAnalysis.diaryPage, diaryPage)
            .leftJoin(diaryPage.diary, diary)
            .where(memberIdEq(memberId), isNotDeleted(), isInRecentDays(14))
            .groupBy(diaryPage.diary.id)
            .orderBy(diaryAnalysis.weight.desc())
            .limit(1);

        // 메인 쿼리: 서브쿼리 결과를 사용하여 최근 3개의 DiaryAnalysis를 가져옴
        List<DiaryAnalysis> results = jpaQueryFactory
            .selectFrom(diaryAnalysis)
            .where(diaryAnalysis.in(subQuery))
            .orderBy(diaryAnalysis.createdDateTime.desc())
            .limit(3)
            .fetch();

        return Optional.ofNullable(results.isEmpty() ? null : results);
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return diary.member.id.eq(memberId);
    }

    private BooleanExpression isNotDeleted() {
        return diaryAnalysis.isDeleted.isFalse();
    }

    private BooleanExpression isInRecentDays(int days) {
        return diaryAnalysis.createdDateTime.after(LocalDateTime.now().minusDays(days));
    }
}
