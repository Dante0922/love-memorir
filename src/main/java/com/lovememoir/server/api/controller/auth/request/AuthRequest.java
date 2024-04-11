package com.lovememoir.server.api.controller.auth.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRequest {

    private String accessToken;

    @Builder
    private AuthRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
