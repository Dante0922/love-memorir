package com.lovememoir.server.common.auth.client;

import com.lovememoir.server.api.controller.auth.response.GoogleUserResponse;
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
public class ClientGoogle implements ClientProxy {

    private final WebClient webClient;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUri;

    @Override
    public Auth createAuth(String accessToken) {
        GoogleUserResponse googleUserResponse = getUserResponse(accessToken);
        return Auth.builder()
            .provider(ProviderType.GOOGLE)
            .providerId(googleUserResponse.getSub())
            .accessToken(accessToken)
            .build();
    }

    public String getProviderId(String accessToken) {
        GoogleUserResponse googleUserResponse = getUserResponse(accessToken);
        return googleUserResponse.getSub();
    }

    private GoogleUserResponse getUserResponse(String accessToken) {
        log.info(accessToken);
        return webClient.get()
            .uri(googleUserInfoUri)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                Mono.error(new AuthException(OAUTH_TOKEN_UNAUTHORIZED)))
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                Mono.error(new AuthException(OAUTH_SERVER_ERROR)))
            .bodyToMono(GoogleUserResponse.class)
            .block();
    }
}
