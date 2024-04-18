package com.lovememoir.server.api.controller.avatar.response;

import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarRefreshResponse {

    private final Emotion emotion;
    private final String question;

    @Builder
    private AvatarRefreshResponse(Emotion emotion, String question) {
        this.emotion = emotion;
        this.question = question;
    }

    public AvatarResponse toAvatarResponse() {
        return AvatarResponse.builder()
            .emotion(emotion)
            .question(question)
            .build();
    }
}
