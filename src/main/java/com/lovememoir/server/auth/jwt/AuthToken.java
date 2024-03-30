package com.lovememoir.server.auth.jwt;

import com.lovememoir.server.domain.member.enumerate.RoleType;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String socialId, RoleType roleType, Date expiry, Key key) {
        String role = roleType.toString();
        this.key = key;
        this.token = createAuthToken(socialId, role, expiry);
    }

    private String createAuthToken(String socialId, String role, Date expiry) {

        return Jwts.builder()
                .setSubject(socialId)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

}
