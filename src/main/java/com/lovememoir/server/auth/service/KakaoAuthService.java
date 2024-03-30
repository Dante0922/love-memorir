package com.lovememoir.server.auth.service;

import com.lovememoir.server.auth.client.ClientKakao;
import com.lovememoir.server.auth.dto.AuthRequest;
import com.lovememoir.server.auth.dto.AuthResponse;
import com.lovememoir.server.auth.jwt.AuthToken;
import com.lovememoir.server.auth.jwt.AuthTokenProvider;
import com.lovememoir.server.domain.OAuth.OAuth;
import com.lovememoir.server.domain.OAuth.repository.OAuthQueryRepository;
import com.lovememoir.server.domain.OAuth.repository.OAuthRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAuthService {

    private final ClientKakao clientKakao;
    private final AuthTokenProvider authTokenProvider;
    private final OAuthRepository oAuthRepository;
    private final OAuthQueryRepository oAuthQueryRepository;
    private final MemberQueryRepository memberQueryRepository;


    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        OAuth kakaoOAuth = clientKakao.getOAuth(authRequest.getAccessToken());
        String providerId = kakaoOAuth.getProviderId();
        AuthToken appToken = authTokenProvider.createUserAppToken(providerId);

        Member member = memberQueryRepository.findByProviderId(providerId);

        log.info("member : {}", kakaoOAuth.getProvider());
        if (member == null) {

            oAuthRepository.save(kakaoOAuth);
            return AuthResponse.builder()
                .appToken(appToken.getToken())
                .isNewMember(Boolean.TRUE)
                .build();
        }

        return AuthResponse.builder()
            .appToken(appToken.getToken())
            .isNewMember(Boolean.FALSE)
            .build();
    }
}
