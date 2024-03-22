package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarRefreshResponse {

    private final int behavior;
    private final String question;

    @Builder
    private AvatarRefreshResponse(int behavior, String question) {
        this.behavior = behavior;
        this.question = question;
    }
}
