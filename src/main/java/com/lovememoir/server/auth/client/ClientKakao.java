package com.lovememoir.server.auth.client;

import com.lovememoir.server.auth.dto.KakaoUserResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientKakao implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Member getUserData(String accessToken) {
        log.info("accessToken: {}", accessToken);

        KakaoUserResponse kakaoUserResponse = webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .headers(h -> h.setBearerAuth(accessToken))
            .retrieve()
            .bodyToMono(KakaoUserResponse.class)
            .block();
        log.info("kakaoUserResponse: {}", kakaoUserResponse.toString());
        log.info("kakaoUserResponse: {}", kakaoUserResponse.getProperties().toString());
        log.info("kakaoUserResponse: {}", kakaoUserResponse.getKakaoAccount().toString());

        return Member.builder()
            .socialId(String.valueOf(kakaoUserResponse.getId()))
            .nickname(kakaoUserResponse.getProperties().getNickname())
            .email(kakaoUserResponse.getKakaoAccount().getEmail())
            .socialType(SocialType.KAKAO)
            .build();

    }
}
