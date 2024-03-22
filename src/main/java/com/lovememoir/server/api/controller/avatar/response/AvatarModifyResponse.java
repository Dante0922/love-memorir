package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyResponse {

    private final Long memberId;
    private final String nickname;
    private final Gender gender;
    private final String birth;

    @Builder
    private MemberModifyResponse(Long memberId, String nickname, Gender gender, String birth) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }
}
