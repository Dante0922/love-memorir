package com.lovememoir.server.domain.diarypage;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.lovememoir.server.domain.diarypage.AnalysisStatus.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisResult {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7)
    private AnalysisStatus analysisStatus;

    private Integer emotionCode;

    @Builder
    private AnalysisResult(AnalysisStatus analysisStatus, Integer emotionCode) {
        this.analysisStatus = analysisStatus;
        this.emotionCode = emotionCode;
    }

    public static AnalysisResult success(int emotionCode) {
        return AnalysisResult.builder()
            .analysisStatus(SUCCESS)
            .emotionCode(emotionCode)
            .build();
    }
}
