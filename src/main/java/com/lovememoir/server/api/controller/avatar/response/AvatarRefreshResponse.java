package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarRefreshResponse {

    private final String behavior;
    private final String question;

    @Builder
    private AvatarRefreshResponse(String behavior, String question) {
        this.behavior = behavior;
        this.question = question;
    }
}
