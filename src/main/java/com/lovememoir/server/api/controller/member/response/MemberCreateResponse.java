package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateResponse {


    private final String nickname;

    @Builder
    private MemberCreateResponse( String nickname) {
        this.nickname = nickname;
    }

    public static MemberCreateResponse of(Member member) {
        return MemberCreateResponse.builder()
            .nickname(member.getNickname())
            .build();
    }
}
