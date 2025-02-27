package com.lovememoir.server.api.service.auth;

import com.lovememoir.server.api.controller.auth.request.AuthRequest;
import com.lovememoir.server.api.controller.auth.response.AuthResponse;
import com.lovememoir.server.common.auth.client.ClientKakao;
import com.lovememoir.server.common.auth.jwt.AuthToken;
import com.lovememoir.server.common.auth.jwt.AuthTokenProvider;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.repository.AuthQueryRepository;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KakaoAuthService {

    private final ClientKakao clientKakao;
    private final AuthTokenProvider authTokenProvider;
    private final AuthRepository authRepository;
    private final AuthQueryRepository authQueryRepository;
    private final MemberQueryRepository memberQueryRepository;


    public AuthResponse login(AuthRequest authRequest) {
        String accessToken = authRequest.getAccessToken();
        String providerId = clientKakao.getProviderId(accessToken);
        Auth savedAuth = authQueryRepository.findByProviderId(providerId);

        if (savedAuth == null) {
            Auth kakaoAuth = clientKakao.createAuth(accessToken);
            authRepository.save(kakaoAuth);
        }

        AuthToken appToken = authTokenProvider.createUserAppToken(providerId);
        Member member = memberQueryRepository.findByProviderId(providerId);

        boolean isNewMember = member == null;
        return AuthResponse.builder()
            .appToken(appToken.getToken())
            .isNewMember(isNewMember)
            .build();
    }
}
