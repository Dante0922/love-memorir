package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyResponse {

    private final String nickname;
    private final String gender;
    private final String birth;

    @Builder
    private MemberModifyResponse( String nickname, String gender, String birth) {
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }

    public static MemberModifyResponse of(Member member){
        return MemberModifyResponse.builder()
            .nickname(member.getNickname())
            .birth(member.getBirth())
            .gender(member.getGender().toString())
            .build();
    }
}
