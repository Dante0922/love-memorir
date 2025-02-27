package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.domain.avatar.Emotion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AvatarApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/avatars";

    @DisplayName("아바타 행동/질문 갱신")
    @Test
    void refreshAvatar() throws Exception {
        //given
        String providerId = "user011";
        AvatarRefreshResponse response = AvatarRefreshResponse.builder()
            .emotion(Emotion.STABILITY)
            .question("오늘은 무슨 일이 있었나요?")
            .build();
        when(avatarService.refreshAvatar(providerId)).thenReturn(response);

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/refresh")
                    .with(user(providerId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.emotion").value("STABILITY"))
            .andExpect(jsonPath("$.data.question").value("오늘은 무슨 일이 있었나요?"));
    }

}