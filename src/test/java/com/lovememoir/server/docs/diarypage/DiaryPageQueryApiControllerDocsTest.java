package com.lovememoir.server.docs.diarypage;

import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.api.controller.diarypage.DiaryPageQueryApiController;
import com.lovememoir.server.api.service.diarypage.DiaryPageQueryService;
import com.lovememoir.server.docs.RestDocsSupport;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.lovememoir.server.common.constant.GlobalConstant.PAGE_SIZE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DiaryPageQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/diaries/{diaryId}/pages";

    private final DiaryPageQueryService diaryPageQueryService = mock(DiaryPageQueryService.class);

    @Override
    protected Object initController() {
        return new DiaryPageQueryApiController(diaryPageQueryService);
    }

    @DisplayName("일기 목록 조회 API")
    @Test
    void searchDiaryPages() throws Exception {
        DiaryPagesResponse response1 = DiaryPagesResponse.builder()
            .diaryPageId(1L)
            .pageTitle("푸바오와 마지막 인사")
            .build();
        DiaryPagesResponse response2 = DiaryPagesResponse.builder()
            .diaryPageId(2L)
            .pageTitle("루이바오의 먹방")
            .build();
        DiaryPagesResponse response3 = DiaryPagesResponse.builder()
            .diaryPageId(3L)
            .pageTitle("후쪽이 후이바오")
            .build();
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
        SliceResponse<DiaryPagesResponse> response = SliceResponse.of(List.of(response3, response2, response1), pageRequest, false);

        given(diaryPageQueryService.searchDiaryPages(anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL, 1L)
                    .param("page", "1")
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-diary-pages",
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
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호")
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
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("조회된 일기 페이지 데이터"),
                    fieldWithPath("data.content[].diaryPageId").type(JsonFieldType.NUMBER)
                        .description("일기 페이지 식별키"),
                    fieldWithPath("data.content[].pageTitle").type(JsonFieldType.STRING)
                        .description("일기 페이지 제목"),
                    fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER)
                        .description("현재 페이지 번호"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("데이터 요청 갯수"),
                    fieldWithPath("data.isFirst").type(JsonFieldType.BOOLEAN)
                        .description("첫 페이지 여부"),
                    fieldWithPath("data.isLast").type(JsonFieldType.BOOLEAN)
                        .description("마지막 페이지 여부")
                )
            ));
    }

    @DisplayName("일기 상세 조회 API")
    @Test
    void searchDiaryPage() throws Exception {
        mockMvc.perform(
                get(BASE_URL + "/{diaryPageId}", 1L, 1L)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-diary-page",
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
                        .description("일기 페이지 식별키"),
                    fieldWithPath("data.pageTitle").type(JsonFieldType.STRING)
                        .description("일기 페이지 제목"),
                    fieldWithPath("data.pageContent").type(JsonFieldType.STRING)
                        .description("일기 페이지 내용"),
                    fieldWithPath("data.diaryDate").type(JsonFieldType.ARRAY)
                        .description("일기 페이지 일자"),
                    fieldWithPath("data.dateTimeOfCreation").type(JsonFieldType.ARRAY)
                        .description("일기 페이지 작성 일시")
                )
            ));
    }
}
