package com.lovememoir.server.api.controller.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateResponse {

    private final Long memberId;
    private final String nickname;

    @Builder
    private MemberCreateResponse(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
