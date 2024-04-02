package com.lovememoir.server.api.service.member.request;

import com.lovememoir.server.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyServiceRequest {
    private final Member member;
    private final String nickname;
    private final String gender;
    private final String birth;

    @Builder
    private MemberModifyServiceRequest(Member member, String nickname, String gender, String birth) {
        this.member = member;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }
}
