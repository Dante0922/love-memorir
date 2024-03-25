package com.lovememoir.server.domain.avatar.repository.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponse {

    private final String avatarType;
    private final String growthStage;
    private final String behavior;
    private final String question;

    @Builder
    public AvatarResponse(String avatarType, String growthStage, String behavior, String question) {
        this.avatarType = avatarType;
        this.growthStage = growthStage;
        this.behavior = behavior;
        this.question = question;
    }
}
