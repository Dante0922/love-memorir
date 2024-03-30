package com.lovememoir.server.auth.client;

import com.lovememoir.server.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ClientKakao implements ClientProxy {

    private final WebClient webClient;

    @Override
    public Member getUserData(String accessToken) {
        return null;
    }
}
