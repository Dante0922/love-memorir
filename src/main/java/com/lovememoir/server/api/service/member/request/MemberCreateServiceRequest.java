package com.lovememoir.server.api.service.member.request;

import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.member.enumerate.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateServiceRequest {


    private final String nickname;
    private final String gender;
    private final String birth;
    private final String email;


    @Builder
    private MemberCreateServiceRequest(String nickname, String gender, String birth, String email) {
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.email = email;

    }
}
