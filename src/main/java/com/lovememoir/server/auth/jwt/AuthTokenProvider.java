package com.lovememoir.server.auth.jwt;

import com.lovememoir.server.domain.member.enumerate.RoleType;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class AuthTokenProvider {

    @Value("${app.auth.token.tokenExpiry}")
    private String expiry;

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(@Value("${app.auth.token.tokenSecret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public AuthToken createToken(String id, RoleType roleType, String expiry) {
        Date expiryDate = getExpiryDate(expiry);
        return new AuthToken(id, roleType, expiryDate, key);
    }

    public AuthToken createUserAppToken(String id) {
        return createToken(id, RoleType.USER, expiry);
    }

    private Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }


}
