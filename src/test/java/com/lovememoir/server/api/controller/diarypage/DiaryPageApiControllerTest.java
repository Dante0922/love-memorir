package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageCreateRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageModifyRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageRemoveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;

import java.time.LocalDate;
import java.util.List;

import static com.lovememoir.server.common.message.ValidationMessage.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
            .recordDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL, 1L)
                    .part(new MockPart("title", request.getTitle().getBytes()))
                    .part(new MockPart("content", request.getContent().getBytes()))
                    .part(new MockPart("recordDate", request.getRecordDate().toString().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
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
            .recordDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL, 1L)
                    .part(new MockPart("title", request.getTitle().getBytes()))
                    .part(new MockPart("content", request.getContent().getBytes()))
                    .part(new MockPart("recordDate", request.getRecordDate().toString().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_BLANK_DIARY_PAGE_CONTENT))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 일기를 등록할 때 기록일은 필수값이다.")
    @Test
    void createDiaryPageWithoutRecordDate() throws Exception {
        //given
        DiaryPageCreateRequest request = DiaryPageCreateRequest.builder()
            .title("푸바오가 떠나는 날")
            .content("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.")
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL, 1L)
                    .part(new MockPart("title", request.getTitle().getBytes()))
                    .part(new MockPart("content", request.getContent().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_NULL_DIARY_PAGE_DATE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 일기를 등록한다.")
    @Test
    void createDiaryPage() throws Exception {
        //given
        DiaryPageCreateRequest request = DiaryPageCreateRequest.builder()
            .title("푸바오가 떠나는 날")
            .content("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.")
            .recordDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL, 1L)
                    .part(new MockPart("title", request.getTitle().getBytes()))
                    .part(new MockPart("content", request.getContent().getBytes()))
                    .part(new MockPart("recordDate", request.getRecordDate().toString().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @DisplayName("일기를 수정할 때 제목은 필수값이다.")
    @Test
    void modifyDiaryPageWithoutTitle() throws Exception {
        //given
        DiaryPageModifyRequest request = DiaryPageModifyRequest.builder()
            .title(" ")
            .content("루이는 판생이 즐거운 미소천사 해피판다!")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{diaryPageId}", 1L, 1L)
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

    @DisplayName("일기를 수정할 때 내용은 필수값이다.")
    @Test
    void modifyDiaryPageWithoutContent() throws Exception {
        //given
        DiaryPageModifyRequest request = DiaryPageModifyRequest.builder()
            .title("햇살미소 뿜뿜하는 루이후이")
            .content(" ")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{diaryPageId}", 1L, 1L)
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

    @DisplayName("일기를 수정할 때 일자는 필수값이다.")
    @Test
    void modifyDiaryPageWithoutDiaryDate() throws Exception {
        //given
        DiaryPageModifyRequest request = DiaryPageModifyRequest.builder()
            .title("햇살미소 뿜뿜하는 루이후이")
            .content("루이는 판생이 즐거운 미소천사 해피판다!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{diaryPageId}", 1L, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_NULL_DIARY_PAGE_DATE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일기를 수정한다.")
    @Test
    void modifyDiaryPage() throws Exception {
        //given
        DiaryPageModifyRequest request = DiaryPageModifyRequest.builder()
            .title("햇살미소 뿜뿜하는 루이후이")
            .content("루이는 판생이 즐거운 미소천사 해피판다!")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{diaryPageId}", 1L, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("일기를 삭제한다.")
    @Test
    void removeDiaryPage() throws Exception {
        //given
        DiaryPageRemoveRequest request = DiaryPageRemoveRequest.builder()
            .diaryPageIds(List.of(1L, 2L, 3L))
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/delete", 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}