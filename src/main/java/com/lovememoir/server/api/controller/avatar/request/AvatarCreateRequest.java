package com.lovememoir.server.api.controller.avatar.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_AVATAR_TYPE;

@Getter
@NoArgsConstructor
public class AvatarCreateRequest {

    @NotNull(message = NOT_NULL_AVATAR_TYPE)
    private String avatarType;

    @Builder
    private AvatarCreateRequest(String avatarType) {
        this.avatarType = avatarType;
    }
}
