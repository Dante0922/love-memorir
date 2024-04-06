package com.lovememoir.server.api.controller.diarypage.response;

import com.lovememoir.server.domain.diarypage.DiaryPage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DiaryPageCreateResponse {

    private final Long diaryPageId;
    private final String title;
    private final int contentLength;
    private final LocalDate diaryDate;
    private final LocalDateTime createdDateTime;

    @Builder
    private DiaryPageCreateResponse(Long diaryPageId, String title, int contentLength, LocalDate diaryDate, LocalDateTime createdDateTime) {
        this.diaryPageId = diaryPageId;
        this.title = title;
        this.contentLength = contentLength;
        this.diaryDate = diaryDate;
        this.createdDateTime = createdDateTime;
    }

    public static DiaryPageCreateResponse of(DiaryPage diaryPage) {
        return DiaryPageCreateResponse.builder()
            .diaryPageId(diaryPage.getId())
            .title(diaryPage.getTitle())
            .contentLength(diaryPage.getContent().length())
            .createdDateTime(diaryPage.getCreatedDateTime())
            .build();
    }
}
