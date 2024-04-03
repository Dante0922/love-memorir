package com.lovememoir.server.docs.diarypage;

import com.lovememoir.server.api.controller.diarypage.DiaryPageApiController;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageCreateRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageModifyRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageRemoveRequest;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageModifyResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageRemoveResponse;
import com.lovememoir.server.api.service.diarypage.DiaryPageService;
import com.lovememoir.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DiaryPageApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/diaries/{diaryId}/pages";
    private final DiaryPageService diaryPageService = mock(DiaryPageService.class);

    @Override
    protected Object initController() {
        return new DiaryPageApiController(diaryPageService);
    }

    @DisplayName("신규 일기 등록 API")
    @Test
    void createDiaryPage() throws Exception {
        DiaryPageCreateRequest request = DiaryPageCreateRequest.builder()
            .title("푸바오가 떠나는 날")
            .content("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        DiaryPageCreateResponse response = DiaryPageCreateResponse.builder()
            .diaryPageId(1L)
            .title("푸바오가 떠나는 날")
            .contentLength("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.".length())
            .diaryDate(LocalDate.of(2024, 3, 3))
            .createdDateTime(LocalDateTime.of(2024, 3, 5, 16, 0))
            .build();

        given(diaryPageService.createDiaryPage(anyString(), anyLong(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-diary-page",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                pathParameters(
                    parameterWithName("diaryId")
                        .description("일기장 식별키")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("신규 일기 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("신규 일기 내용"),
                    fieldWithPath("diaryDate").type(JsonFieldType.ARRAY)
                        .description("신규 일기 날짜")
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
                    fieldWithPath("data.diaryPageId").type(JsonFieldType.NUMBER)
                        .description("신규 일기 식별키"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("신규 일기 제목"),
                    fieldWithPath("data.contentLength").type(JsonFieldType.NUMBER)
                        .description("신규 일기 내용의 길이"),
                    fieldWithPath("data.diaryDate").type(JsonFieldType.ARRAY)
                        .description("신규 일기 일자"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("신규 일기 등록일시")
                )
            ));
    }

    @DisplayName("일기 수정 API")
    @Test
    void modifyDiaryPage() throws Exception {
        DiaryPageModifyRequest request = DiaryPageModifyRequest.builder()
            .title("햇살미소 뿜뿜하는 루이후이")
            .content("루이는 판생이 즐거운 미소천사 해피판다!")
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        DiaryPageModifyResponse response = DiaryPageModifyResponse.builder()
            .diaryPageId(1L)
            .title("햇살미소 뿜뿜하는 루이후이")
            .contentLength("루이는 판생이 즐거운 미소천사 해피판다!".length())
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();

        given(diaryPageService.modifyDiaryPage(anyString(), anyLong(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/{diaryPageId}", 1, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-diary-page",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                pathParameters(
                    parameterWithName("diaryId")
                        .description("일기장 식별키"),
                    parameterWithName("diaryPageId")
                        .description("일기 식별키")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("수정할 일기 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("수정할 일기 내용"),
                    fieldWithPath("diaryDate").type(JsonFieldType.ARRAY)
                        .description("수정할 일기 날짜")
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
                    fieldWithPath("data.diaryPageId").type(JsonFieldType.NUMBER)
                        .description("수정된 일기 식별키"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("수정된 일기 제목"),
                    fieldWithPath("data.contentLength").type(JsonFieldType.NUMBER)
                        .description("수정된 일기 내용의 길이"),
                    fieldWithPath("data.diaryDate").type(JsonFieldType.ARRAY)
                        .description("수정된 일기 일자")
                )
            ));
    }

    @DisplayName("일기 삭제 API")
    @Test
    void removeDiaryPage() throws Exception {
        DiaryPageRemoveRequest request = DiaryPageRemoveRequest.builder()
            .diaryPageIds(List.of(1L, 2L, 3L))
            .build();

        DiaryPageRemoveResponse response = DiaryPageRemoveResponse.builder()
            .removedPageCount(3)
            .build();

        given(diaryPageService.removeDiaryPage(anyList()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/delete", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-diary-page",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                pathParameters(
                    parameterWithName("diaryId")
                        .description("일기장 식별키")
                ),
                requestFields(
                    fieldWithPath("diaryPageIds").type(JsonFieldType.ARRAY)
                        .description("삭제할 일기 식별키")
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
                    fieldWithPath("data.removedPageCount").type(JsonFieldType.NUMBER)
                        .description("삭제된 일기 갯수")
                )
            ));
    }
}
