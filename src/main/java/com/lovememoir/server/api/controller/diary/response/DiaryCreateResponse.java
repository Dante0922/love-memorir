package com.lovememoir.server.api.controller.diary.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryCreateResponse {

    private final Long diaryId;
    private final String title;
    private final LocalDateTime createdDateTime;

    @Builder
    private DiaryCreateResponse(Long diaryId, String title, LocalDateTime createdDateTime) {
        this.diaryId = diaryId;
        this.title = title;
        this.createdDateTime = createdDateTime;
    }
}
