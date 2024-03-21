package com.lovememoir.server.api.controller.diarypage.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiaryPageModifyResponse {

    private final Long diaryPageId;
    private final String title;
    private final int contentLength;
    private final LocalDate diaryDate;

    @Builder
    private DiaryPageModifyResponse(Long diaryPageId, String title, int contentLength, LocalDate diaryDate) {
        this.diaryPageId = diaryPageId;
        this.title = title;
        this.contentLength = contentLength;
        this.diaryDate = diaryDate;
    }
}
