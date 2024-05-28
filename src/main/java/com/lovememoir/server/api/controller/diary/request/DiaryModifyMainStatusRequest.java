package com.lovememoir.server.api.controller.diary.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryModifyMainStatusRequest {

    private Boolean isMain;

    @Builder
    private DiaryModifyMainStatusRequest(Boolean isMain) {
        this.isMain = isMain;
    }
}
