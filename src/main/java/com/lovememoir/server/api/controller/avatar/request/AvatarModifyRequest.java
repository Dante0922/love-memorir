package com.lovememoir.server.api.controller.avatar.request;

import com.lovememoir.server.domain.member.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.lovememoir.server.common.message.ValidationMessage.*;

@Getter
@NoArgsConstructor
public class AvatarModifyRequest {

    @NotNull(message = NOT_NULL_AVATAR_TYPE)
    private String avatarType;
    @NotNull(message = NOT_NULL_AVATAR_GROWTH_STAGE)
    private String growthStage;

    @Builder
    private AvatarModifyRequest(String avatarType, String growthStage) {
        this.avatarType = avatarType;
        this.growthStage = growthStage;
    }
}
