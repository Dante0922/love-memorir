package com.lovememoir.server.docs.member;

import com.lovememoir.server.api.controller.member.MemberQueryApiController;
import com.lovememoir.server.api.service.member.MemberQueryService;
import com.lovememoir.server.docs.RestDocsSupport;
import com.lovememoir.server.domain.member.repository.response.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/members";
    private final MemberQueryService memberQueryService = mock(MemberQueryService.class);

    @Override
    protected Object initController() {
        return new MemberQueryApiController(memberQueryService);
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void searchMember() throws Exception {

        MemberResponse response = MemberResponse.builder()
            .nickname("연해말")
            .gender("F")
            .birth("1990-01-01")
            .build();

        given(memberQueryService.searchMember(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "user.authorization.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-member",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("응답 코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("응답 메시지"),
                        fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                            .description("닉네임"),
                        fieldWithPath("data.gender").type(JsonFieldType.STRING)
                            .description("신규 멤버 성별 (M 또는 F)"),
                        fieldWithPath("data.birth").type(JsonFieldType.STRING)
                            .description("신규 멤버 생년월일")
                    )
                )
            );
    }
}
