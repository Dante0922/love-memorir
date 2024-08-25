package com.lovememoir.server.api.controller.auth;

import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.auth.request.AuthRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/auth";

    @DisplayName("카카오 로그인을 한다.")
    @Test
    void kakaoAuthLogin() throws Exception {
        //given
        AuthRequest request = AuthRequest.builder()
            .accessToken("accessToken")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/kakao")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}