package com.lovememoir.server.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateServiceRequest {


    private final String nickname;
    private final String gender;
    private final String birth;
    private final String email;
    private final String authId;


    @Builder
    private MemberCreateServiceRequest(String authId,String nickname, String gender, String birth, String email) {
        this.authId = authId;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.email = email;

    }
}
