package com.lovememoir.server.domain.diarypage.repository.response;

import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class DiaryPagesResponse {

    private final Long diaryPageId;
    private final AnalysisStatus analysisStatus;
    private final Integer emotionCode;
    private final String title;
    private final LocalDate recordDate;
    private final LocalDateTime createdDateTime;
    private String emotionName;

    @Builder
    private DiaryPagesResponse(Long diaryPageId, AnalysisStatus analysisStatus, Integer emotionCode, String title, LocalDate recordDate, LocalDateTime createdDateTime, String emotion) {
        this.diaryPageId = diaryPageId;
        this.analysisStatus = analysisStatus;
        this.emotionCode = emotionCode;
        this.title = title;
        this.recordDate = recordDate;
        this.createdDateTime = createdDateTime;
        this.emotionName = emotionCode == null ? "" : Emotion.fromCode(emotionCode).toString();
    }

    public void setEmotionString(int emotionCode) {
        this.emotionName = Emotion.fromCode(emotionCode).toString();
    }
}
