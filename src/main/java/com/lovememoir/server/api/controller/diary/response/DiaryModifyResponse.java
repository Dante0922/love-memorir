package com.lovememoir.server.api.controller.diary.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryModifyResponse {

    private final Long diaryId;
    private final String title;

    @Builder
    private DiaryModifyResponse(Long diaryId, String title) {
        this.diaryId = diaryId;
        this.title = title;
    }
}
