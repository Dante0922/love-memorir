package com.lovememoir.server.api.controller.diary;

import com.lovememoir.server.ControllerTestSupport;
import com.lovememoir.server.api.controller.diary.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryImageModifyRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_DIARY_TITLE;
import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_IS_LOVE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
            .isLove(false)
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

    @DisplayName("신규 일기장을 등록할 때 연애 여부는 필수값이다.")
    @Test
    void createDiaryWithoutIsInLove() throws Exception {
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
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(NOT_NULL_IS_LOVE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 일기장을 등록한다.")
    @Test
    void createDiary() throws Exception {
        //given
        DiaryCreateRequest request = DiaryCreateRequest.builder()
            .title("푸바오")
            .isLove(false)
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
            .isLove(false)
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

    @DisplayName("일기장 정보를 수정할 때 연애 여부은 필수값이다.")
    @Test
    void modifyDiaryWithoutRelationshipStartedDate() throws Exception {
        //given
        DiaryModifyRequest request = DiaryModifyRequest.builder()
            .title("푸바오")
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
            .andExpect(jsonPath("$.message").value(NOT_NULL_IS_LOVE))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일기장 정보를 수정한다.")
    @Test
    void modifyDiary() throws Exception {
        //given
        DiaryModifyRequest request = DiaryModifyRequest.builder()
            .title("루이바오")
            .isLove(false)
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

    @DisplayName("일기장 프로필 이미지를 수정한다.")
    @Test
    void modifyDiaryImage() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile(
            "profile",
            "diary-profile-upload-image.jpg",
            "image/jpg",
            "image data".getBytes()
        );

        DiaryImageModifyRequest request = DiaryImageModifyRequest.builder()
            .profile(file)
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL + "/{diaryId}", 1L)
                    .file(file)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("일기장을 보관 상태를 수정한다.")
    @Test
    void modifyDiaryStoreStatus() throws Exception {
        //given //when //then
        mockMvc.perform(
                delete(BASE_URL + "/{diaryId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("일기장을 삭제한다.")
    @Test
    void removeDiary() throws Exception {
        //given //when //then
        mockMvc.perform(
                delete(BASE_URL + "/{diaryId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}