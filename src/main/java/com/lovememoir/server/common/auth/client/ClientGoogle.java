package com.lovememoir.server.common.auth.client;

import com.lovememoir.server.api.controller.auth.response.GoogleUserResponse;
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
public class ClientGoogle implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Auth createAuth(String accessToken) {
        GoogleUserResponse googleUserResponse = getUserResponse(accessToken);
        return Auth.builder()
            .provider(ProviderType.GOOGLE)
            .providerId(googleUserResponse.getSub())
            // TODO : 프론트로부터 RefreshToken 받아오기?
            .accessToken(accessToken)
            .build();
    }

    public String getProviderId(String accessToken) {
        GoogleUserResponse kakaoUserResponse = getUserResponse(accessToken);
        return kakaoUserResponse.getSub();
    }

    private GoogleUserResponse getUserResponse(String accessToken) {
        return webClient.get()
            .uri("https://oauth2.googleapis.com/tokeninfo",
                builder ->
                    builder
                        .queryParam("id_token", accessToken)
                        .build())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new AuthException(OAUTH_TOKEN_UNAUTHORIZED)))
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new AuthException(OAUTH_SERVER_ERROR)))
            .bodyToMono(GoogleUserResponse.class)
            .block();
    }
}
