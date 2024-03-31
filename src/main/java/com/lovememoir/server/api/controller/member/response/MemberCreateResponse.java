package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Member;
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

    public static MemberCreateResponse of(Member member) {
        return MemberCreateResponse.builder()
            .memberKey(member.getMemberKey())
            .nickname(member.getNickname())
            .build();
    }
}
