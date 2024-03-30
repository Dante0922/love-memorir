package com.lovememoir.server.auth.client;

import com.lovememoir.server.auth.dto.KakaoUserResponse;
import com.lovememoir.server.domain.OAuth.OAuth;
import com.lovememoir.server.domain.OAuth.ProviderType;
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
    public OAuth getOAuth(String accessToken) {
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

        return OAuth.builder()
            .provider(ProviderType.KAKAO)
            .providerId(String.valueOf(kakaoUserResponse.getId()))
            // TODO : 프론트로부터 RefreshToken 받아오기?
            .accessToken(accessToken)
            .build();

    }
}
