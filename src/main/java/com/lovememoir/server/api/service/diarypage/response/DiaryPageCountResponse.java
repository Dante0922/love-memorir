package com.lovememoir.server.api.service.diarypage.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryPageCountResponse {

    private final int pageCount;

    @Builder
    private DiaryPageCountResponse(int pageCount) {
        this.pageCount = pageCount;
    }
}
