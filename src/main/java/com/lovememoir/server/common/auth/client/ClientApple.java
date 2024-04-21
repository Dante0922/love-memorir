package com.lovememoir.server.common.auth.client;

import com.lovememoir.server.api.controller.auth.response.AppleUserResponse;
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
public class ClientApple implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Auth createAuth(String accessToken) {
        AppleUserResponse appleUserResponse = getUserResponse(accessToken);
        return Auth.builder()
            .provider(ProviderType.APPLE)
            .providerId(appleUserResponse.getId().toString())
            .accessToken(accessToken)
            .build();
    }

    public String getProviderId(String accessToken) {
        AppleUserResponse appleUserResponse = getUserResponse(accessToken);
        return appleUserResponse.getId().toString();
    }

    private AppleUserResponse getUserResponse(String accessToken) {

        //TODO 아래 URL 참고해서 Apple 사용자정보 받아오는 값 적용하기
        // https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/authenticating_users_with_sign_in_with_apple#3383773

        return webClient.get()
            .uri("${spring.security.oauth2.client.provider.apple.user-info-uri}")
            .headers(h -> h.setBearerAuth(accessToken))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new AuthException(OAUTH_TOKEN_UNAUTHORIZED)))
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new AuthException(OAUTH_SERVER_ERROR)))
            .bodyToMono(AppleUserResponse.class)
            .block();
    }
}
