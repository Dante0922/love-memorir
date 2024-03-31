package com.lovememoir.server.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateServiceRequest {

    private final String nickname;
    private final String gender;
    private final String birth;

    @Builder
    private MemberCreateServiceRequest(String nickname, String gender, String birth) {
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }
}
