package com.lovememoir.server.api.controller;

import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.request.DiaryModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_DIARY_TITLE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DiaryApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/diaries";

    @DisplayName("신규 일기장을 등록할 때 제목(파트너 닉네임)은 필수값이다.")
    @Test
    void createDiaryWithoutTitle() throws Exception {
        //given
        DiaryCreateRequest request = DiaryCreateRequest.builder()
            .title(" ")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_BLANK_DIARY_TITLE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 일기장을 등록한다.")
    @Test
    void createDiary() throws Exception {
        //given
        DiaryCreateRequest request = DiaryCreateRequest.builder()
            .title("푸바오")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @DisplayName("일기장 정보를 수정할 때 제목(파트너 닉네임)은 필수값이다.")
    @Test
    void modifyDiaryWithoutTitle() throws Exception {
        //given
        DiaryModifyRequest request = DiaryModifyRequest.builder()
            .title(" ")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{diaryId}", 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_BLANK_DIARY_TITLE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일기장 정보를 수정한다.")
    @Test
    void modifyDiary() throws Exception {
        //given
        DiaryCreateRequest request = DiaryCreateRequest.builder()
            .title("루이바오")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{diaryId}", 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}