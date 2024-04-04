package com.lovememoir.server.api.controller.member.request;

import com.lovememoir.server.api.service.member.request.MemberCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.lovememoir.server.common.message.ValidationMessage.*;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = NOT_BLANK_MEMBER_NICKNAME)
    private String nickname;
    @NotNull(message = NOT_NULL_MEMBER_GENDER)
    private String gender;
    @NotBlank(message = NOT_BLANK_MEMBER_BIRTH)
    private String birth;


    @Builder
    private MemberCreateRequest(String nickname, String gender, String birth) {
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }

    public MemberCreateServiceRequest toServiceRequest(String authId) {
        return MemberCreateServiceRequest.builder()
            .authId(authId)
            .nickname(nickname)
            .gender(gender)
            .birth(birth)
            .build();
    }
}
