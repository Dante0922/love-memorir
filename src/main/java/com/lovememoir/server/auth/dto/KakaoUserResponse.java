package com.lovememoir.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserResponse {

    private Long id;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Getter
    public static class Properties {
        private String nickname;
        private String profileImage;
    }

    @Getter
    public static class KakaoAccount {
        private String email;
    }
}
