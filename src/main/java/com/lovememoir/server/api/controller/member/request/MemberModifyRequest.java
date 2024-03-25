package com.lovememoir.server.api.controller.member.request;

import com.lovememoir.server.domain.member.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.lovememoir.server.common.message.ValidationMessage.*;

@Getter
@NoArgsConstructor
public class MemberModifyRequest {

    @NotNull(message = NOT_NULL_MEMBER_ID)
    private Long memberId;
    @NotBlank(message = NOT_BLANK_MEMBER_NICKNAME)
    private String nickname;
    @NotNull(message = NOT_NULL_MEMBER_GENDER)
    private String gender;
    @NotBlank(message = NOT_BLANK_MEMBER_BIRTH)
    private String birth;


    @Builder
    private MemberModifyRequest(Long memberId, String nickname, String gender, String birth) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
    }
}
