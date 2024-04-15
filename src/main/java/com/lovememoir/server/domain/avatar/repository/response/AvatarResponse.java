package com.lovememoir.server.domain.avatar.repository.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponse {

    private final String emotion;
    private final String question;

    @Builder
    public AvatarResponse(String emotion, String question) {
        this.emotion = behavior;
        this.question = question;
    }
}
