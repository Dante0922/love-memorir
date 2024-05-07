package com.lovememoir.server.common.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovememoir.server.common.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

import static com.lovememoir.server.common.message.ExceptionMessage.FAILED_TO_GENERATE_TOKEN;

public class JwtParser {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    public static Map<String, String> parseHeaders(String token) {
        try {
            String encodedHeader = token.split(TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
            String decodedHeader = new String(Base64.getDecoder().decode(encodedHeader));
            return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
            throw new AuthException(FAILED_TO_GENERATE_TOKEN);
        }
    }

    public static Claims parseClaims(String idToken, PublicKey publicKey) {
        try {
            return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(idToken)
                .getPayload();
        } catch (AuthException e) {
            throw new AuthException(FAILED_TO_GENERATE_TOKEN);
        }
    }
}
