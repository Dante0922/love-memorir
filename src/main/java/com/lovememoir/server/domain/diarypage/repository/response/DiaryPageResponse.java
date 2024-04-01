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
    private final String pageTitle;
    private final String pageContent;
    private final LocalDate diaryDate;
    private final LocalDateTime createdDateTime;

    @Builder
    private DiaryPageResponse(Long diaryPageId, AnalysisStatus analysisStatus, Integer emotionCode, String pageTitle, String pageContent, LocalDate diaryDate, LocalDateTime createdDateTime) {
        this.diaryPageId = diaryPageId;
        this.analysisStatus = analysisStatus;
        this.emotionCode = emotionCode;
        this.pageTitle = pageTitle;
        this.pageContent = pageContent;
        this.diaryDate = diaryDate;
        this.createdDateTime = createdDateTime;
    }
}
