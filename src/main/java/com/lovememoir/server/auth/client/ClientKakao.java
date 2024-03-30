package com.lovememoir.server.auth.client;

import com.lovememoir.server.auth.dto.KakaoUserResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ClientKakao implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .headers(h -> h.setBearerAuth(accessToken))
            .retrieve()
            .bodyToMono(KakaoUserResponse.class)
            .block();

        return Member.builder()
            .socialId(String.valueOf(kakaoUserResponse.getId()))
            .nickname(kakaoUserResponse.getProperties().getNickname())
            .email(kakaoUserResponse.getKakaoAccount().getEmail())
            .socialType(SocialType.KAKAO)
            .build();

    }
}
