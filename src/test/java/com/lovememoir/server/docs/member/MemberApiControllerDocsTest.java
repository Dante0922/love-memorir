package com.lovememoir.server.docs.member;

import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.domain.member.Gender;
import com.lovememoir.server.api.controller.member.MemberApiController;
import com.lovememoir.server.api.controller.member.request.MemberCreateRequest;
import com.lovememoir.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerDocsTest extends RestDocsSupport {


    private static final String BASE_URL = "/api/v1/members";

    @Override
    protected Object initController() {
        return new MemberApiController();
    }

    @DisplayName("신규 멤버 가입 API")
    @Test
    void createMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
                .nickname("연해말")
                .gender(Gender.F)
                .birth("1990-01-01")
                .build();

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
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER)
                                        .description("신규 멤버 식별키"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                        .description("신규 멤버 닉네임")
                        )
                ));
    }

    @DisplayName("멤버 정보 수정 API")
    @Test
    void modifyMember() throws Exception {
        MemberModifyRequest request = MemberModifyRequest.builder()
                .memberId(1L)
                .nickname("연해말")
                .gender(Gender.F)
                .birth("1990-01-01")
                .build();

        mockMvc.perform(
                        patch(BASE_URL + "/{memberId}", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("modify-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION)
                                        .description("회원 인증 토큰")
                        ),
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER)
                                        .description("멤버 식별키"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("멤버 수정 닉네임"),
                                fieldWithPath("gender").type(JsonFieldType.STRING)
                                        .description("멤버 수정 성별 (M 또는 F)"),
                                fieldWithPath("birth").type(JsonFieldType.STRING)
                                        .description("멤버 수정 생년월일")
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
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER)
                                        .description("멤버 식별키"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                        .description("멤버 수정 닉네임"),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING)
                                        .description("멤버 수정 성별 (M 또는 F)"),
                                fieldWithPath("data.birth").type(JsonFieldType.STRING)
                                        .description("멤버 수정 생년월일")
                        )
                ));
    }

    @DisplayName("멤버 탈퇴 API")
    @Test
    void removeMember() throws Exception {
        mockMvc.perform(
                        delete(BASE_URL + "/{memberId}", 1L)
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
                        pathParameters(
                                parameterWithName("memberId")
                                        .description("멤버 식별키")
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
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER)
                                        .description("삭제 멤버 식별키"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                        .description("삭제 멤버 닉네임")
                        )
                ));
    }
}
