package com.lovememoir.server.api.controller.auth;

import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.auth.request.AuthRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryImageModifyRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_DIARY_TITLE;
import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_IS_LOVE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/auth";

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