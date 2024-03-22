package com.lovememoir.server.api.controller.avatar;
import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.avatar.request.AvatarCreateRequest;
import com.lovememoir.server.api.controller.avatar.request.AvatarModifyRequest;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AvatarApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/avatars";

    @DisplayName("새로운 아바타 생성")
    @Test
    void createAvatar() throws Exception {
        //given
        AvatarCreateRequest request = AvatarCreateRequest.builder()
                .avatarType(1)
                .build();
        //when //then
        mockMvc.perform(
                        post(BASE_URL + "/{memberId}", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                    )
                    .andDo(print())
                    .andExpect(status().isCreated());
    }

    @DisplayName("아바타 정보 변경")
    @Test
    void modifyAvatar() throws Exception {
        //given
        AvatarModifyRequest request = AvatarModifyRequest.builder()
                .avatarType(1)
                .growthStage(1)
                .build();
        //when //then
        mockMvc.perform(
                        patch(BASE_URL + "/{memberId}", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("아바타 행동/질문 갱신")
    @Test
    void refreshAvatar() throws Exception {
        //given
        AvatarRefreshResponse request = AvatarRefreshResponse.builder()
                .behavior(2)
                .question("오늘은 무슨 일이 있었나요?")
                .build();
        //when //then
        mockMvc.perform(
                        get(BASE_URL + "/{memberId}/refresh", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}