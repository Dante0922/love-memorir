package com.lovememoir.server.docs.avatar;

import com.lovememoir.server.api.controller.avatar.AvatarQueryApiController;
import com.lovememoir.server.api.service.avatar.AvatarQueryService;
import com.lovememoir.server.docs.RestDocsSupport;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.anyString;
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

public class AvatarQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/avatars";
    private final AvatarQueryService avatarQueryService = mock(AvatarQueryService.class);


    @Override
    protected Object initController() {
        return new AvatarQueryApiController(avatarQueryService);
    }

    @DisplayName("아바타 조회 API")
    @Test
    void searchAvatar() throws Exception {

        AvatarResponse response = AvatarResponse.builder()
            .emotion(Emotion.STABILITY)
            .question("하이")
            .build();

        BDDMockito.given(avatarQueryService.searchAvatar(anyString()))
            .willReturn(response);


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
                    fieldWithPath("data.emotion").type(JsonFieldType.STRING)
                        .description("아바타 감정"),
                    fieldWithPath("data.question").type(JsonFieldType.STRING)
                        .description("아바타 질문")
                )
            ));
    }
}
