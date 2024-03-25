package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyResponse {

    private final String memberKey;
    private final String nickname;
    private final String gender;
    private final String birth;

    @Builder
    private MemberModifyResponse(String memberKey, String nickname, String gender, String birth) {
        this.memberKey = memberKey;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }
}
