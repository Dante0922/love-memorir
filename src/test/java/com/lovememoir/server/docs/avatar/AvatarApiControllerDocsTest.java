package com.lovememoir.server.docs.avatar;

import com.lovememoir.server.api.controller.avatar.AvatarApiController;
import com.lovememoir.server.api.controller.avatar.request.AvatarCreateRequest;
import com.lovememoir.server.api.controller.avatar.request.AvatarModifyRequest;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AvatarApiControllerDocsTest extends RestDocsSupport {


    private static final String BASE_URL = "/api/v1/avatars";

    @Override
    protected Object initController() {
        return new AvatarApiController();
    }

    @DisplayName("신규 아바타 생성 API")
    @Test
    void createAvatar() throws Exception {
        AvatarCreateRequest request = AvatarCreateRequest.builder()
                .avatarType(1)
                .build();

        mockMvc.perform(
                        post(BASE_URL + "/{memberId}", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-avatar",
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
                        requestFields(
                                fieldWithPath("avatarType").type(JsonFieldType.NUMBER)
                                        .description("신규 아바타 타입")
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
                                fieldWithPath("data.avatarType").type(JsonFieldType.NUMBER)
                                        .description("신규 아바타 타입")
                        )
                ));
    }

    @DisplayName("아바타 정보 변경 API")
    @Test
    void modifyAvatar() throws Exception {
        AvatarModifyRequest request = AvatarModifyRequest.builder()
                .avatarType(1)
                .growthStage(1)
                .build();

        mockMvc.perform(
                        patch(BASE_URL + "/{memberId}", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("modify-avatar",
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
                        requestFields(
                                fieldWithPath("avatarType").type(JsonFieldType.NUMBER)
                                        .description("변경 아바타 타입"),
                                fieldWithPath("growthStage").type(JsonFieldType.NUMBER)
                                        .description("변경 아바타 성장등급")
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
                                fieldWithPath("data.avatarType").type(JsonFieldType.NUMBER)
                                        .description("변경 아바타 타입"),
                                fieldWithPath("data.growthStage").type(JsonFieldType.NUMBER)
                                        .description("변경 아바타 성장등급")
                        )
                ));
    }

    @DisplayName("아바타 행동/질문 갱신 API")
    @Test
    void refreshAvatar() throws Exception {
        AvatarModifyRequest request = AvatarModifyRequest.builder()
                .avatarType(1)
                .growthStage(1)
                .build();

        mockMvc.perform(
                        get(BASE_URL + "/{memberId}/refresh", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("refresh-avatar",
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
                                fieldWithPath("data.behavior").type(JsonFieldType.NUMBER)
                                        .description("변경 아바타 행동"),
                                fieldWithPath("data.question").type(JsonFieldType.STRING)
                                        .description("변경 아바타 질문")
                        )
                ));
    }
}
