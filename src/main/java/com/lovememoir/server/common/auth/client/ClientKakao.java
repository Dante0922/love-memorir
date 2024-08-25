package com.lovememoir.server.common.auth.client;

import com.lovememoir.server.api.controller.auth.response.KakaoUserResponse;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    @Override
    public Auth createAuth(String accessToken) {
        KakaoUserResponse kakaoUserResponse = getUserResponse(accessToken);
        return Auth.builder()
            .provider(ProviderType.KAKAO)
            .providerId(kakaoUserResponse.getId().toString())
            // TODO : 프론트로부터 RefreshToken 받아오기?
            .accessToken(accessToken)
            .build();
    }

    public String getProviderId(String accessToken) {
        KakaoUserResponse kakaoUserResponse = getUserResponse(accessToken);
        return kakaoUserResponse.getId().toString();
    }

    private KakaoUserResponse getUserResponse(String accessToken) {
        log.info("accessToken {}", accessToken);
        return webClient.get()
            .uri(kakaoUserInfoUri)
            .headers(h -> h.setBearerAuth(accessToken))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new AuthException(OAUTH_TOKEN_UNAUTHORIZED)))
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new AuthException(OAUTH_SERVER_ERROR)))
            .bodyToMono(KakaoUserResponse.class)
            .block();
    }
}
