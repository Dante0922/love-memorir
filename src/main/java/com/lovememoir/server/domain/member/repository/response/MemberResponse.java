package com.lovememoir.server.domain.member.repository.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final String nickname;
    private final String gender;
    private final String birth;

    @Builder
    private MemberResponse(String nickname, String gender, String birth) {
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }
}
