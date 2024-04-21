package com.lovememoir.server.domain.avatar.repository.response;

import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.Emotion;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponse {

    private final Emotion emotion;
    private final String question;

    @Builder
    public AvatarResponse(Emotion emotion, String question) {
        this.emotion = emotion;
        this.question = question;
    }

    public static AvatarResponse of(Avatar avatar) {
        return AvatarResponse.builder()
            .emotion(avatar.getEmotion())
            .question(avatar.getQuestion())
            .build();
    }
}
