package com.lovememoir.server.api.controller.auth.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AppleUserResponse {

    private Long id;
    private Properties properties;
    private AppleAccount appleAccount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Properties {
        private String nickname;
        private String profileImage;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class AppleAccount {
        private String email;
    }
}
