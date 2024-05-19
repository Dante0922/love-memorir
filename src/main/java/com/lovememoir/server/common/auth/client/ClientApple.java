package com.lovememoir.server.common.auth.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovememoir.server.common.auth.jwt.JwtParser;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

import static com.lovememoir.server.common.message.ExceptionMessage.FAILED_TO_GENERATE_TOKEN;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientApple implements ClientProxy {

    private final WebClient webClient;
    @Value("${spring.security.oauth2.client.provider.apple.user-info-uri}")
    private String appleUserInfoUri;
    private static final String APPLE_JWKS_URL = "https://appleid.apple.com/auth/keys";


    @Override
    public Auth createAuth(String idToken) {
        String providerId = getAppleSubject(idToken);
        return Auth.builder()
            .provider(ProviderType.APPLE)
            .providerId(providerId)
            .build();
    }

    public String getProviderId(String idToken) {
        return getAppleSubject(idToken);
    }

    private String getAppleSubject(String idToken) {
        Map<String, String> tokenHeaders = JwtParser.parseHeaders(idToken);
        String kid = tokenHeaders.get("kid");
        RSAPublicKey applePublicKey = getApplePublicKey(kid);
        Claims claims = JwtParser.parseClaims(idToken, applePublicKey);
        validateClaims(claims);

        // ProviderId == Subject
        return claims.getSubject();

    }

    private void validateClaims(Claims claims) {
        throw new AuthException("validateClaims 만드는 중..");
        // TODO 구현할 것..
    }

    private RSAPublicKey getApplePublicKey(String keyId) {
        Mono<String> responseMono = webClient.get()
            .uri(APPLE_JWKS_URL)
            .retrieve()
            .bodyToMono(String.class);

        try {
            String jwksResponse = responseMono.block();


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jwksJson = objectMapper.readTree(jwksResponse);

            JsonNode keys = jwksJson.get("keys");
            for (JsonNode key : keys) {
                if (keyId.equals(key.get("kid").asText())) {
                    // RSA 공개 키 생성
                    String n = key.get("n").asText();
                    String e = key.get("e").asText();

                    byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
                    byte[] exponentBytes = Base64.getUrlDecoder().decode(e);

                    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                        new BigInteger(1, modulusBytes),
                        new BigInteger(1, exponentBytes)
                    );

                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                    return (RSAPublicKey) publicKey;
                }
            }
        } catch (Exception e) {
            throw new AuthException(FAILED_TO_GENERATE_TOKEN);
        }
        return null;
    }
}
