package com.lovememoir.server.docs.diary;

import com.lovememoir.server.api.controller.diary.DiaryQueryApiController;
import com.lovememoir.server.api.service.diary.DiaryQueryService;
import com.lovememoir.server.docs.RestDocsSupport;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DiaryQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/diaries";
    private final DiaryQueryService diaryQueryService = mock(DiaryQueryService.class);

    @Override
    protected Object initController() {
        return new DiaryQueryApiController(diaryQueryService);
    }

    @DisplayName("일기장 목록 조회 API")
    @Test
    void searchDiaries() throws Exception {
        DiarySearchResponse response1 = DiarySearchResponse.builder()
            .diaryId(1L)
            .isMain(true)
            .title("러바오와의 연애 기록")
            .profileImage("profile-image-url")
            .pageCount(10)
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .build();
        DiarySearchResponse response2 = DiarySearchResponse.builder()
            .diaryId(2L)
            .isMain(false)
            .title("푸바오와의 연애 기록")
            .profileImage("profile-image-url")
            .pageCount(7)
            .relationshipStartedDate(LocalDate.of(2020, 7, 20))
            .build();
        DiarySearchResponse response3 = DiarySearchResponse.builder()
            .diaryId(3L)
            .isMain(false)
            .title("쌍둥바오와의 연애 기록")
            .profileImage("profile-image-url")
            .pageCount(13)
            .relationshipStartedDate(LocalDate.of(2023, 7, 7))
            .build();

        given(diaryQueryService.searchDiaries(anyString()))
            .willReturn(List.of(response3, response2, response1));

        mockMvc.perform(
                get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-diaries",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("등록된 일기장 갯수"),
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("등록된 일기장 데이터"),
                    fieldWithPath("data.content[].diaryId").type(JsonFieldType.NUMBER)
                        .description("일기장 식별키"),
                    fieldWithPath("data.content[].isMain").type(JsonFieldType.BOOLEAN)
                        .description("일기장 메인 여부"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                        .description("일기장 제목"),
                    fieldWithPath("data.content[].profileImage").type(JsonFieldType.STRING)
                        .description("일기장 프로필 이미지"),
                    fieldWithPath("data.content[].pageCount").type(JsonFieldType.NUMBER)
                        .description("일기장 페이지 수"),
                    fieldWithPath("data.content[].relationshipStartedDate").type(JsonFieldType.ARRAY)
                        .description("일기장 연애 시작일")
                )
            ));
    }

    @DisplayName("메인 일기장 목록 조회 API")
    @Test
    void searchMainDiaries() throws Exception {
        DiarySearchResponse response = DiarySearchResponse.builder()
            .diaryId(1L)
            .isMain(true)
            .title("푸바오와의 연애 기록")
            .profileImage("profile-image-url")
            .pageCount(10)
            .relationshipStartedDate(LocalDate.of(2020, 7, 20))
            .build();

        given(diaryQueryService.searchMainDiaries(anyString()))
            .willReturn(List.of(response));

        mockMvc.perform(
                get(BASE_URL + "/main")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-main-diaries",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("등록된 일기장 갯수"),
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("등록된 일기장 데이터"),
                    fieldWithPath("data.content[].diaryId").type(JsonFieldType.NUMBER)
                        .description("일기장 식별키"),
                    fieldWithPath("data.content[].isMain").type(JsonFieldType.BOOLEAN)
                        .description("일기장 메인 여부"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                        .description("일기장 제목"),
                    fieldWithPath("data.content[].profileImage").type(JsonFieldType.STRING)
                        .description("일기장 프로필 이미지"),
                    fieldWithPath("data.content[].pageCount").type(JsonFieldType.NUMBER)
                        .description("일기장 페이지 수"),
                    fieldWithPath("data.content[].relationshipStartedDate").type(JsonFieldType.ARRAY)
                        .description("일기장 연애 시작일")
                )
            ));
    }
}
