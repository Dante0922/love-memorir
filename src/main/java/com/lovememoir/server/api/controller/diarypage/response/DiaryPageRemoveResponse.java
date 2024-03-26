package com.lovememoir.server.api.controller.diarypage.response;

import com.lovememoir.server.domain.diarypage.DiaryPage;
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

    public static DiaryPageRemoveResponse of(DiaryPage diaryPage) {
        return DiaryPageRemoveResponse.builder()
            .diaryPageId(diaryPage.getId())
            .title(diaryPage.getTitle())
            .build();
    }
}
