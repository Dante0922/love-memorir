package com.lovememoir.server.api.controller.avatar.response;

import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarCreateResponse {

    private final String emotion;
    private final String question;

    @Builder
    private AvatarCreateResponse(String emotion, String question) {
        this.emotion = emotion;
        this.question = question;
    }

    public AvatarResponse toAvatarResponse() {
        return AvatarResponse.builder()
            .emotion(emotion)
            .question(question)
            .build();
    }

    public static AvatarCreateResponse
}
