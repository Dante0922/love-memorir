package com.lovememoir.server.common.auth.jwt;

import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static com.lovememoir.server.common.message.ExceptionMessage.FAILED_TO_GENERATE_TOKEN;

@Component
public class AuthTokenProvider {

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${app.auth.token.tokenExpiry}")
    private String expiry;

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(CustomUserDetailsService customUserDetailsService, @Value("${app.auth.token.tokenSecret}") String secretKey) {
        this.customUserDetailsService = customUserDetailsService;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public AuthToken createUserAppToken(String id) {
        return createToken(id, RoleType.USER, expiry);
    }

    public AuthToken createToken(String id, RoleType roleType, String expiry) {
        Date expiryDate = getExpiryDate(expiry);
        return new AuthToken(id, roleType, expiryDate, key);
    }

    private Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }



    public Authentication getAuthentication(AuthToken token) {

        if(token.validate()) {
            Claims claims = token.getTokenClaims();
            UserDetails principal = customUserDetailsService.loadUserByUsername(claims.getSubject());
//            Collection<? extends SimpleGrantedAuthority> authorities = Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
//                .map(SimpleGrantedAuthority::new)
//                .toList();

            return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
        } else {
            throw new AuthException(FAILED_TO_GENERATE_TOKEN);
        }
    }
}
