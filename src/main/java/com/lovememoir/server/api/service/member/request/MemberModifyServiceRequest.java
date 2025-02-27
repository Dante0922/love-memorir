package com.lovememoir.server.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyServiceRequest {
    private final String nickname;
    private final String gender;
    private final String birth;
    private final String providerId;

    @Builder
    private MemberModifyServiceRequest(String nickname, String gender, String birth, String providerId) {
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.providerId = providerId;
    }
}
