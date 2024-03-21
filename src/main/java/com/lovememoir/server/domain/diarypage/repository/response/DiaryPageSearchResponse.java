package com.lovememoir.server.domain.diarypage.repository.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryPageSearchResponse {

    private final Long diaryPageId;
    private final String pageTitle;

    @Builder
    public DiaryPageSearchResponse(Long diaryPageId, String pageTitle) {
        this.diaryPageId = diaryPageId;
        this.pageTitle = pageTitle;
    }
}
