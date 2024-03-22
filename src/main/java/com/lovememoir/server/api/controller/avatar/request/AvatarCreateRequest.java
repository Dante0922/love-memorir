package com.lovememoir.server.api.controller.avatar.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_AVATAR_TYPE;

@Getter
@NoArgsConstructor
public class AvatarCreateRequest {

    @NotNull(message = NOT_NULL_AVATAR_TYPE)
    private int avatarType;

    @Builder
    private AvatarCreateRequest(int avatarType) {
        this.avatarType = avatarType;
    }
}
