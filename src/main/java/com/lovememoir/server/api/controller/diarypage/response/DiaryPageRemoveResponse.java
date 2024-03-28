package com.lovememoir.server.api.controller.diarypage.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryPageRemoveResponse {

    private final int removedPageCount;

    @Builder
    private DiaryPageRemoveResponse(int removedPageCount) {
        this.removedPageCount = removedPageCount;
    }

    public static DiaryPageRemoveResponse of(int removedPageCount) {
        return DiaryPageRemoveResponse.builder()
            .removedPageCount(removedPageCount)
            .build();
    }
}
