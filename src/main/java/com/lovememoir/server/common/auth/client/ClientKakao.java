package com.lovememoir.server.common.auth.client;

import com.lovememoir.server.api.controller.auth.response.KakaoUserResponse;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.lovememoir.server.common.message.ExceptionMessage.OAUTH_SERVER_ERROR;
import static com.lovememoir.server.common.message.ExceptionMessage.OAUTH_TOKEN_UNAUTHORIZED;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientKakao implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Auth getOAuth(String accessToken) {
        log.info("accessToken: {}", accessToken);

        KakaoUserResponse kakaoUserResponse = webClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .headers(h -> h.setBearerAuth(accessToken))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new AuthException(OAUTH_TOKEN_UNAUTHORIZED)))
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new AuthException(OAUTH_SERVER_ERROR)))
            .bodyToMono(KakaoUserResponse.class)
            .block();
        log.info("kakaoUserResponse: {}", kakaoUserResponse.toString());
        log.info("kakaoUserResponse: {}", kakaoUserResponse.getProperties().toString());
        log.info("kakaoUserResponse: {}", kakaoUserResponse.getKakaoAccount().toString());

        return Auth.builder()
            .id(kakaoUserResponse.getId().toString())
            .provider(ProviderType.KAKAO)
            // TODO : 프론트로부터 RefreshToken 받아오기?
            .accessToken(accessToken)
            .build();

    }
}
