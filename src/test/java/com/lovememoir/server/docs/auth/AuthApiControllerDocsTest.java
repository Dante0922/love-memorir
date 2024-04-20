package com.lovememoir.server.docs.auth;

import com.lovememoir.server.api.controller.auth.AuthApiController;
import com.lovememoir.server.api.controller.auth.request.AuthRequest;
import com.lovememoir.server.api.controller.auth.response.AuthResponse;
import com.lovememoir.server.api.service.auth.AppleAuthService;
import com.lovememoir.server.api.service.auth.GoogleAuthService;
import com.lovememoir.server.api.service.auth.KakaoAuthService;
import com.lovememoir.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthApiControllerDocsTest extends RestDocsSupport {


    private static final String BASE_URL = "/auth";
    private final KakaoAuthService kakaoAuthService = mock(KakaoAuthService.class);
    private final AppleAuthService appleAuthService = mock(AppleAuthService.class);
    private final GoogleAuthService googleAuthService = mock(GoogleAuthService.class);


    @Override
    protected Object initController() {
        return new AuthApiController(kakaoAuthService, appleAuthService, googleAuthService);
    }

    @DisplayName("카카오 OAuth2 인증 API")
    @Test
    void kakaoAuthLogin() throws Exception {

        AuthRequest request = AuthRequest.builder()
            .accessToken("kakao.access.token")
            .build();

        AuthResponse response = AuthResponse.builder()
            .appToken("app.token")
            .isNewMember(true)
            .build();


        given(kakaoAuthService.login(any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/kakao")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("kakao-login",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("accessToken").type(JsonFieldType.STRING)
                        .description("카카오 access token")
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
                    fieldWithPath("data.appToken").type(JsonFieldType.STRING)
                        .description("앱 전용 토큰"),
                    fieldWithPath("data.isNewMember").type(JsonFieldType.BOOLEAN)
                        .description("신규 멤버 여부")
                )
            ));
    }
}
