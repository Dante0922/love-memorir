package com.lovememoir.server.api.controller.diarypage.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryPageRemoveRequest {

    private List<Long> diaryPageIds = new ArrayList<>();

    @Builder
    private DiaryPageRemoveRequest(List<Long> diaryPageIds) {
        this.diaryPageIds = diaryPageIds;
    }
}
