package com.lovememoir.server.api.controller.auth.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AppleUserResponse {

    private String accessToken;
    private long expiresIn;
    private String idToken;
    private String refreshToken;
    private String tokenType;
    private AppleAccount appleAccount;

    @Getter
    public static class AppleAccount {
        private String iss;
        private String aud;
        private long exp;
        private long iat;
        private String sub;
        private String atHash;
        private String email;
        private boolean emailVerified;
        private boolean isPrivateEmail;
        private long authTime;
    }
}
