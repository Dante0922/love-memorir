package com.lovememoir.server.docs.avatar;

import com.lovememoir.server.api.controller.avatar.AvatarApiController;
import com.lovememoir.server.api.controller.avatar.AvatarQueryApiController;
import com.lovememoir.server.api.controller.avatar.request.AvatarCreateRequest;
import com.lovememoir.server.api.controller.avatar.request.AvatarModifyRequest;
import com.lovememoir.server.docs.RestDocsSupport;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
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

public class AvatarQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/avatars";

    @Override
    protected Object initController() {
        return new AvatarQueryApiController();
    }

    @DisplayName("아바타 조회 API")
    @Test
    void searchAvatar() throws Exception {
        mockMvc.perform(
                get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-avatar",
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
                    fieldWithPath("data.avatarType").type(JsonFieldType.NUMBER)
                        .description("아바타 타입"),
                    fieldWithPath("data.growthStage").type(JsonFieldType.NUMBER)
                        .description("아바타 성장등급"),
                    fieldWithPath("data.behavior").type(JsonFieldType.NUMBER)
                        .description("아바타 행동"),
                    fieldWithPath("data.question").type(JsonFieldType.STRING)
                        .description("아바타 질문")
                )
            ));
    }
}
