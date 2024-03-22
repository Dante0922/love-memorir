package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarCreateResponse {

    private final Long memberId;
    private final int avatarType;

    @Builder
    private AvatarCreateResponse(Long memberId, int avatarType) {
        this.memberId = memberId;
        this.avatarType = avatarType;
    }
}
