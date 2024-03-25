package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRemoveResponse {

    private final String memberKey;
    private final String nickname;


    @Builder
    private MemberRemoveResponse(String memberKey, String nickname) {
        this.memberKey = memberKey;
        this.nickname = nickname;
    }
}
