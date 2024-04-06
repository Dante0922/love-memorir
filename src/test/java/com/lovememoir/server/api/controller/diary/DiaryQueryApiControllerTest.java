package com.lovememoir.server.api.controller.diary;

import com.lovememoir.server.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DiaryQueryApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/diaries";

    @DisplayName("회원의 일기장 목록을 조회한다.")
    @Test
    void searchDiaries() throws Exception {
        //given //when //then
        mockMvc.perform(
                get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}