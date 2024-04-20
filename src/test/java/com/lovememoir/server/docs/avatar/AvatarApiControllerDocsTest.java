package com.lovememoir.server.docs.avatar;

import com.lovememoir.server.api.controller.avatar.AvatarApiController;
import com.lovememoir.server.api.service.avatar.AvatarService;
import com.lovememoir.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AvatarApiControllerDocsTest extends RestDocsSupport {


    private static final String BASE_URL = "/api/v1/avatars";
    private final AvatarService avatarService = mock(AvatarService.class);

    @Override
    protected Object initController() {
        return new AvatarApiController(avatarService);
    }


    @DisplayName("아바타 감정/질문 갱신 API")
    @Test
    void refreshAvatar() throws Exception {

        mockMvc.perform(
                get(BASE_URL + "/refresh")

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
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.emotion").type(JsonFieldType.STRING)
                        .description("변경 아바타 행동"),
                    fieldWithPath("data.question").type(JsonFieldType.STRING)
                        .description("변경 아바타 질문")
                )
            ));
    }
}
