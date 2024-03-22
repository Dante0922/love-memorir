package com.lovememoir.server.api.controller.avatar;
import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.avatar.request.AvatarCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

}