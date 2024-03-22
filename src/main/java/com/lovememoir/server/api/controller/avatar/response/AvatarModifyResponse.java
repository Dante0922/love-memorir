package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarModifyResponse {

    private final int avatarType;
    private final int growthStage;

    @Builder
    private AvatarModifyResponse(int avatarType, int growthStage) {
        this.avatarType = avatarType;
        this.growthStage = growthStage;
    }
}
