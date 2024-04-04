package com.lovememoir.server.docs.member;

import com.lovememoir.server.WithAuthMember;
import com.lovememoir.server.api.controller.member.MemberApiController;
import com.lovememoir.server.api.controller.member.request.MemberCreateRequest;
import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.service.member.MemberService;
import com.lovememoir.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerDocsTest extends RestDocsSupport {


    private static final String BASE_URL = "/api/v1/members";
    private final MemberService memberService = mock(MemberService.class);


    @Override
    protected Object initController() {
        return new MemberApiController(memberService);
    }

    @DisplayName("신규 멤버 가입 API")
    @Test
    @WithAuthMember
    void createMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .nickname("연해말")
            .gender("F")
            .birth("1990-01-01")
            .build();
        MemberCreateResponse response = MemberCreateResponse.builder()
            .nickname("연해말")
            .build();

        //TODO 왜 어노테이션이 안 먹지??
        String providerId = "user.authorization.token";
        String role = "ROLE_USER";
        User principal = new User(providerId, "", List.of(new SimpleGrantedAuthority(role)));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        //TODO 위 부분 공통화 할 것

        given(memberService.createMember(any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                requestFields(
                    fieldWithPath("nickname").type(JsonFieldType.STRING)
                        .description("신규 멤버 닉네임"),
                    fieldWithPath("gender").type(JsonFieldType.STRING)
                        .description("신규 멤버 성별 (M 또는 F)"),
                    fieldWithPath("birth").type(JsonFieldType.STRING)
                        .description("신규 멤버 생년월일")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                        .description("신규 멤버 닉네임")
                )
            ));
    }

//    @DisplayName("멤버 정보 수정 API")
//    @Test
//    void modifyMember() throws Exception {
//        MemberModifyRequest request = MemberModifyRequest.builder()
//            .nickname("연해말")
//            .gender("F")
//            .birth("1990-01-01")
//            .build();
//
//        mockMvc.perform(
//                patch(BASE_URL)
//                    .content(objectMapper.writeValueAsString(request))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
//            )
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andDo(document("modify-member",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                requestHeaders(
//                    headerWithName(HttpHeaders.AUTHORIZATION)
//                        .description("회원 인증 토큰")
//                ),
//                requestFields(
//                    fieldWithPath("nickname").type(JsonFieldType.STRING)
//                        .description("멤버 수정 닉네임"),
//                    fieldWithPath("gender").type(JsonFieldType.STRING)
//                        .description("멤버 수정 성별 (M 또는 F)"),
//                    fieldWithPath("birth").type(JsonFieldType.STRING)
//                        .description("멤버 수정 생년월일")
//                ),
//                responseFields(
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
//                    fieldWithPath("message").type(JsonFieldType.STRING)
//                        .description("메시지"),
//                    fieldWithPath("data").type(JsonFieldType.OBJECT)
//                        .description("응답 데이터"),
//                    fieldWithPath("data.nickname").type(JsonFieldType.STRING)
//                        .description("멤버 수정 닉네임"),
//                    fieldWithPath("data.gender").type(JsonFieldType.STRING)
//                        .description("멤버 수정 성별 (M 또는 F)"),
//                    fieldWithPath("data.birth").type(JsonFieldType.STRING)
//                        .description("멤버 수정 생년월일")
//                )
//            ));
//    }

    @DisplayName("멤버 탈퇴 API")
    @Test
    void removeMember() throws Exception {
        mockMvc.perform(
                delete(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.memberKey").type(JsonFieldType.STRING)
                        .description("삭제 멤버 식별키"),
                    fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                        .description("삭제 멤버 닉네임")
                )
            ));
    }
}
