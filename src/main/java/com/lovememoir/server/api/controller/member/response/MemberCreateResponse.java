package com.lovememoir.server.api.controller.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateResponse {

    private final String memberKey;
    private final String nickname;

    @Builder
    private MemberCreateResponse(String memberKey, String nickname) {
        this.memberKey = memberKey;
        this.nickname = nickname;
    }
}
