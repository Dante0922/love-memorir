package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarCreateResponse {

    private final int avatarType;

    @Builder
    private AvatarCreateResponse(int avatarType) {
        this.avatarType = avatarType;
    }
}
