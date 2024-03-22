package com.lovememoir.server.api.controller.member;

import com.lovememoir.server.api.controller.member.request.MemberCreateRequest;
import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.domain.member.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lovememoir.server.ControllerTestSupport;
import org.springframework.http.MediaType;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_MEMBER_NICKNAME;
import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_MEMBER_ID;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/members";

    @Test
    @DisplayName("신규 멤버를 등록한다.")
    void createMember() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .nickname("연해말")
                .birth("1990-01-01")
                .gender(Gender.F)
                .build();
        //when //then
        mockMvc.perform(
                        post(BASE_URL)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("신규 멤버를 등록할 때 닉네임은 필수값이다.")
    void createMemberWithoutNickname() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .nickname(" ")
                .birth("1990-01-01")
                .gender(Gender.F)
                .build();
        //when //then
        mockMvc.perform(
                        post(BASE_URL)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value(NOT_BLANK_MEMBER_NICKNAME))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("멤버 정보를 수정한다")
    void modifyMember() throws Exception {
        //given
        MemberModifyRequest request = MemberModifyRequest.builder()
                .memberId(1L)
                .nickname("hello")
                .birth("1990-01-01")
                .gender(Gender.M)
                .build();
        //when //then
        mockMvc.perform(
                        patch(BASE_URL + "/{memberId}", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 정보를 수정한다")
    void modifyMemberWithoutMemberId() throws Exception {
        //given
        MemberModifyRequest request = MemberModifyRequest.builder()
                .nickname("hello")
                .birth("1990-01-01")
                .gender(Gender.M)
                .build();
        //when //then
        mockMvc.perform(
                        patch(BASE_URL + "/{memberId}", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value(NOT_NULL_MEMBER_ID))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}