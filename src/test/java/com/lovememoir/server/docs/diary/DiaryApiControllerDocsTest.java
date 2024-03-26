package com.lovememoir.server.docs.diary;

import com.lovememoir.server.api.controller.diary.DiaryApiController;
import com.lovememoir.server.api.controller.diary.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryImageModifyRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryModifyRequest;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.DiaryService;
import com.lovememoir.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DiaryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/diaries";
    private final DiaryService diaryService = mock(DiaryService.class);

    @Override
    protected Object initController() {
        return new DiaryApiController(diaryService);
    }

    @DisplayName("신규 일기장 등록 API")
    @Test
    void createDiary() throws Exception {
        DiaryCreateRequest request = DiaryCreateRequest.builder()
            .title("푸바오")
            .relationshipStartedDate(LocalDate.of(2024, 1, 1))
            .build();

        DiaryCreateResponse response = DiaryCreateResponse.builder()
            .diaryId(1L)
            .title("푸바오와의 연애 기록")
            .createdDateTime(LocalDateTime.of(2024, 3, 1, 0, 0))
            .build();

        given(diaryService.createDiary(anyString(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-diary",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 인증 토큰")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("신규 일기장 제목"),
                    fieldWithPath("relationshipStartedDate").type(JsonFieldType.ARRAY)
                        .description("신규 일기장 연애 시작일")
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
                    fieldWithPath("data.diaryId").type(JsonFieldType.NUMBER)
                        .description("신규 일기장 식별키"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("신규 일기장 제목"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("신규 일기장 등록일시")
                )
            ));
    }

    @DisplayName("일기장 정보 수정 API")
    @Test
    void modifyDiary() throws Exception {
        DiaryModifyRequest request = DiaryModifyRequest.builder()
            .title("루이바오")
            .relationshipStartedDate(LocalDate.of(2024, 1, 1))
            .build();

        DiaryModifyResponse response = DiaryModifyResponse.builder()
            .diaryId(1L)
            .title("루이바오와의 연애 기록")
            .build();

        given(diaryService.modifyDiary(anyString(), anyLong(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/{diaryId}", 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-diary",
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
                        .description("수정할 일기장 제목"),
                    fieldWithPath("relationshipStartedDate").type(JsonFieldType.ARRAY)
                        .description("수정할 일기장 연애 시작일")
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
                    fieldWithPath("data.diaryId").type(JsonFieldType.NUMBER)
                        .description("수정된 일기장 식별키"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("수정된 일기장 제목")
                )
            ));
    }

    @DisplayName("일기장 이미지 수정 API")
    @Test
    void modifyDiaryImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "profile",
            "diary-profile-upload-image.jpg",
            "image/jpg",
            "image data".getBytes()
        );

        DiaryImageModifyRequest request = DiaryImageModifyRequest.builder()
            .profile(file)
            .build();

        DiaryModifyResponse response = DiaryModifyResponse.builder()
            .diaryId(1L)
            .title("루이바오와의 연애 기록")
            .build();

        given(diaryService.modifyDiaryImage(anyString(), anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                multipart(BASE_URL + "/{diaryId}", 1L)
                    .file(file)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-diary-image",
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
                requestParts(
                    partWithName("profile")
                        .optional()
                        .description("일기장 프로필 사진")
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
                    fieldWithPath("data.diaryId").type(JsonFieldType.NUMBER)
                        .description("수정된 일기장 식별키"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("수정된 일기장 제목")
                )
            ));
    }

    @DisplayName("일기장 삭제 API")
    @Test
    void removeDiary() throws Exception {
        DiaryRemoveResponse response = DiaryRemoveResponse.builder()
            .diaryId(1L)
            .title("후이바오")
            .build();

        given(diaryService.removeDiary(anyString(), anyLong()))
            .willReturn(response);

        mockMvc.perform(
                delete(BASE_URL + "/{diaryId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-diary",
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
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.diaryId").type(JsonFieldType.NUMBER)
                        .description("삭제된 일기장 식별키"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("삭제된 일기장 제목")
                )
            ));
    }
}
