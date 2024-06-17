package com.lovememoir.server.api.service.auth;

import com.lovememoir.server.common.auth.jwt.AuthToken;
import com.lovememoir.server.common.auth.jwt.AuthTokenProvider;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.repository.AuthQueryRepository;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.lovememoir.server.common.message.ExceptionMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final AuthQueryRepository authQueryRepository;
    private final AuthRepository authRepository;

    public Long getMemberId(String token) {
        AuthToken authToken = authTokenProvider.convertAuthToken(token);

        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        try {
//            Member member = memberQueryRepository.findByProviderId(claims.getSubject());
//            return member.getId();
            return null;
        } catch (NullPointerException e) {
            throw new AuthException(USER_NOT_FOUND);
        }
    }
    public void removeAuthByProviderId(String providerId) {
        Auth auth = authQueryRepository.findByProviderId(providerId);
        if (auth == null) {
            throw new AuthException(USER_NOT_FOUND);
        }
        authRepository.delete(auth);
    }
}
