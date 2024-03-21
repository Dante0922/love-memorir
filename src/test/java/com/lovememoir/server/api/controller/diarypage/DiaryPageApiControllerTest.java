package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.diary.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DiaryPageApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/diaries/{diaryId}/pages";

    @DisplayName("신규 일기를 등록할 때 제목은 필수값이다.")
    @Test
    void createDiaryPageWithoutTitle() throws Exception {
        //given
        DiaryPageCreateRequest request = DiaryPageCreateRequest.builder()
            .title(" ")
            .content("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_BLANK_DIARY_PAGE_TITLE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 일기를 등록할 때 내용은 필수값이다.")
    @Test
    void createDiaryPageWithoutContent() throws Exception {
        //given
        DiaryPageCreateRequest request = DiaryPageCreateRequest.builder()
            .title("푸바오가 떠나는 날")
            .content(" ")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_BLANK_DIARY_PAGE_CONTENT))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 일기를 등록할 때 일자는 필수값이다.")
    @Test
    void createDiaryPageWithoutDiaryDate() throws Exception {
        //given
        DiaryPageCreateRequest request = DiaryPageCreateRequest.builder()
            .title("푸바오가 떠나는 날")
            .content("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_BLANK_DIARY_PAGE_DATE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 일기를 등록한다.")
    @Test
    void createDiaryPage() throws Exception {
        //given
        DiaryPageCreateRequest request = DiaryPageCreateRequest.builder()
            .title("푸바오가 떠나는 날")
            .content("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }
}