package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRemoveResponse {

    private final Long memberId;
    private final String nickname;


    @Builder
    private MemberRemoveResponse(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
