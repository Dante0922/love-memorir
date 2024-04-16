package com.lovememoir.server.domain.diaryanalysis.repository;

import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.lovememoir.server.domain.diaryanalysis.QDiaryAnalysis.diaryAnalysis;

@Repository
@RequiredArgsConstructor
public class DiaryAnalysisQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<DiaryAnalysis> findTop3RecentAnalysesByMemberId(Long memberId) {
        return jpaQueryFactory
            .selectFrom(diaryAnalysis)
            .where(diaryAnalysis.diaryPage.diary.member.id.eq(memberId),
                diaryAnalysis.isDeleted.isFalse(),
                diaryAnalysis.createdDateTime.after(LocalDateTime.now().minusDays(14)))
            .orderBy(diaryAnalysis.createdDateTime.desc())
            .limit(3)
            .fetch();
    }


}
