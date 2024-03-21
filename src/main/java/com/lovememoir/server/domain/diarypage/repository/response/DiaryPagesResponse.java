package com.lovememoir.server.domain.diarypage.repository.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryPagesResponse {

    private final Long diaryPageId;
    private final String pageTitle;

    @Builder
    public DiaryPagesResponse(Long diaryPageId, String pageTitle) {
        this.diaryPageId = diaryPageId;
        this.pageTitle = pageTitle;
    }
}
