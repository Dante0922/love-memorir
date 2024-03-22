package com.lovememoir.server.docs.member;

import com.lovememoir.server.api.controller.member.request.Gender;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
}
