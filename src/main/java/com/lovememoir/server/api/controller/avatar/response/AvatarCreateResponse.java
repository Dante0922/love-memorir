package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarCreateResponse {

    private final String avatarType;

    @Builder
    private AvatarCreateResponse(String avatarType) {
        this.avatarType = avatarType;
    }
}
