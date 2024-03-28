package com.lovememoir.server.domain.diaryanalysis;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryAnalysis extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_analysis_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer emotionCode;

    @Column(nullable = false)
    private Integer weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_page_id")
    private DiaryPage diaryPage;

    @Builder
    private DiaryAnalysis(boolean isDeleted, int emotionCode, int weight, DiaryPage diaryPage) {
        super(isDeleted);
        this.emotionCode = emotionCode;
        this.weight = weight;
        this.diaryPage = diaryPage;
    }

    public static DiaryAnalysis create(int emotionCode, int weight, DiaryPage diaryPage) {
        return DiaryAnalysis.builder()
            .emotionCode(emotionCode)
            .weight(weight)
            .diaryPage(diaryPage)
            .build();
    }
}
