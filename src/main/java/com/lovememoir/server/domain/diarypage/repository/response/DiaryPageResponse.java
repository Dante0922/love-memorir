package com.lovememoir.server.domain.diarypage.repository.response;

import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class DiaryPageResponse {

    private final Long diaryPageId;
    private final AnalysisStatus analysisStatus;
    private final Integer emotionCode;
    private final String title;
    private final String content;
    private final LocalDate recordDate;
    private final LocalDateTime createdDateTime;

    @Builder
    private DiaryPageResponse(Long diaryPageId, AnalysisStatus analysisStatus, Integer emotionCode, String title, String content, LocalDate recordDate, LocalDateTime createdDateTime) {
        this.diaryPageId = diaryPageId;
        this.analysisStatus = analysisStatus;
        this.emotionCode = emotionCode;
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
        this.createdDateTime = createdDateTime;
    }
}
