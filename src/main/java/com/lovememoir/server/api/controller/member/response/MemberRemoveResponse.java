package com.lovememoir.server.api.controller.member.response;

import com.lovememoir.server.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRemoveResponse {

    private final String nickname;
    private final String message;


    @Builder
    private MemberRemoveResponse(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }

    public static MemberRemoveResponse of(Member member){
        return MemberRemoveResponse.builder()
            .nickname(member.getNickname())
            .message("탈퇴가 정상적으로 처리되었습니다.")
            .build();
    }
}
