package com.lovememoir.server.api.controller.member;

import com.lovememoir.server.WithAuthMember;
import com.lovememoir.server.api.controller.member.request.MemberCreateRequest;
import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lovememoir.server.ControllerTestSupport;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_MEMBER_NICKNAME;
import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_MEMBER_ID;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/members";

    @MockBean
    MemberRepository memberRepository;

    @DisplayName("신규 멤버를 등록한다.")
    @Test
    void createMember() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .nickname("연해말")
                .birth("1990-01-01")
                .gender("F")
                .build();
        //when //then
        mockMvc.perform(
                        post(BASE_URL)
                                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzNDE0MTM2MDE0Iiwicm9sZSI6IlVTRVIiLCJleHAiOjE3MTIxNTExNzd9.xraMNB1ZF5qibU2NqFDTaG7UPUR9zVGBFkfQaskCP4o")
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
                .gender("F")
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
    @WithAuthMember
    void modifyMember() throws Exception {
        //given
        MemberModifyRequest request = MemberModifyRequest.builder()
                .nickname("hello")
                .birth("1990-01-01")
                .gender("M")
                .build();
        //when //then
        mockMvc.perform(
                        patch(BASE_URL)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버를 삭제한다")
    void removeMember() throws Exception {
        //given //when //then
        mockMvc.perform(
                        delete(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}