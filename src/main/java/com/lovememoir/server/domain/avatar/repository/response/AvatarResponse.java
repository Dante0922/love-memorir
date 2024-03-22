package com.lovememoir.server.domain.avatar.repository.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponse {

    private final Long memberId;
    private final int avatarType;
    private final int growthStage;
    private final int behavior;
    private final String question;

    @Builder
    public AvatarResponse(Long memberId, int avatarType, int growthStage, int behavior, String question) {
        this.memberId = memberId;
        this.avatarType = avatarType;
        this.growthStage = growthStage;
        this.behavior = behavior;
        this.question = question;
    }
}
