package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarModifyResponse {

    private final String avatarType;
    private final String growthStage;

    @Builder
    private AvatarModifyResponse(String avatarType, String growthStage) {
        this.avatarType = avatarType;
        this.growthStage = growthStage;
    }
}
