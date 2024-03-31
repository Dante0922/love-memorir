package com.lovememoir.server.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String appToken;
    private Boolean isNewMember;
}
