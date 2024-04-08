package com.lovememoir.server.api.service.diarypage.response;

import com.lovememoir.server.domain.attachedimage.repository.response.AttachedImageResponse;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DiaryPageResponse {

    private final long diaryPageId;
    private final AnalysisStatus analysisStatus;
    private final Integer emotionCode;
    private final String title;
    private final String content;
    private final LocalDate recordDate;
    private final List<AttachedImageResponse> images;
    private final LocalDateTime createdDateTime;

    @Builder
    private DiaryPageResponse(long diaryPageId, AnalysisStatus analysisStatus, Integer emotionCode, String title, String content, LocalDate recordDate, List<AttachedImageResponse> images, LocalDateTime createdDateTime) {
        this.diaryPageId = diaryPageId;
        this.analysisStatus = analysisStatus;
        this.emotionCode = emotionCode;
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
        this.images = images;
        this.createdDateTime = createdDateTime;
    }

    public static DiaryPageResponse of(DiaryPageDto diaryPage, List<AttachedImageResponse> images) {
        return DiaryPageResponse.builder()
            .diaryPageId(diaryPage.getDiaryPageId())
            .analysisStatus(diaryPage.getAnalysisStatus())
            .emotionCode(diaryPage.getEmotionCode())
            .title(diaryPage.getTitle())
            .content(diaryPage.getContent())
            .recordDate(diaryPage.getRecordDate())
            .images(images)
            .createdDateTime(diaryPage.getCreatedDateTime())
            .build();
    }
}
