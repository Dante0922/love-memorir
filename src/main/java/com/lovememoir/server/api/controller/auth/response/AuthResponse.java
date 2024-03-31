package com.lovememoir.server.api.controller.auth.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String appToken;
    private Boolean isNewMember;
}
