package com.lovememoir.server.api.controller.avatar.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarRefreshResponse {

    private final String emotion;
    private final String question;

    @Builder
    private AvatarRefreshResponse(String emotion, String question) {
        this.emotion = emotion;
        this.question = question;
    }
}
