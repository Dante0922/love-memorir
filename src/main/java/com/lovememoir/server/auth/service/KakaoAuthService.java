package com.lovememoir.server.auth.service;

import com.lovememoir.server.auth.client.ClientKakao;
import com.lovememoir.server.auth.dto.AuthRequest;
import com.lovememoir.server.auth.dto.AuthResponse;
import com.lovememoir.server.auth.jwt.AuthToken;
import com.lovememoir.server.auth.jwt.AuthTokenProvider;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final ClientKakao clientKakao;
    private final MemberQueryRepository memberQueryRepository;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        Member kakaoMember = clientKakao.getUserData(authRequest.getAccessToken());
        String socialId = kakaoMember.getSocialId();
        Member member = memberQueryRepository.findBySocialId(socialId);

        AuthToken appToken = authTokenProvider.createUserAppToken(socialId);

        if (member == null) {
            memberRepository.save(kakaoMember);
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
