package com.lovememoir.server.api.controller.diarypage.response;

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
}
