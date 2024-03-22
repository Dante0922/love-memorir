package com.lovememoir.server.api.controller.diarypage.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryPageRemoveResponse {

    private final Long diaryPageId;
    private final String title;

    @Builder
    private DiaryPageRemoveResponse(Long diaryPageId, String title) {
        this.diaryPageId = diaryPageId;
        this.title = title;
    }
}
