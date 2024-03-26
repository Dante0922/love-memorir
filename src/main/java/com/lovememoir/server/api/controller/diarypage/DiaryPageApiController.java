package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageCreateRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageModifyRequest;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageModifyResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageRemoveResponse;
import com.lovememoir.server.api.service.diarypage.DiaryPageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Diary Page Command Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries/{diaryId}/pages")
public class DiaryPageApiController {

    private final DiaryPageService diaryPageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiaryPageCreateResponse> createDiaryPage(
        @PathVariable Long diaryId,
        @Valid @RequestBody DiaryPageCreateRequest request
    ) {
        //TODO: 2024-03-26 14:40 dong82 회원 정보 토큰 추출
        String memberKey = UUID.randomUUID().toString();

        LocalDateTime currentDateTime = LocalDateTime.now();

        DiaryPageCreateResponse response = diaryPageService.createDiaryPage(memberKey, diaryId, currentDateTime, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{diaryPageId}")
    public ApiResponse<DiaryPageModifyResponse> modifyDiaryPage(
        @PathVariable Long diaryId,
        @PathVariable Long diaryPageId,
        @Valid @RequestBody DiaryPageModifyRequest request
    ) {
        DiaryPageModifyResponse response = DiaryPageModifyResponse.builder()
            .diaryPageId(1L)
            .title("햇살미소 뿜뿜하는 루이후이")
            .contentLength("루이는 판생이 즐거운 미소천사 해피판다!".length())
            .diaryDate(LocalDate.of(2024, 3, 3))
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{diaryPageId}")
    public ApiResponse<DiaryPageRemoveResponse> removeDiaryPage(
        @PathVariable Long diaryId,
        @PathVariable Long diaryPageId
    ) {
        DiaryPageRemoveResponse response = DiaryPageRemoveResponse.builder()
            .diaryPageId(2L)
            .title("엄마 음식 훔쳐간 후이바오")
            .build();
        return ApiResponse.ok(response);
    }
}
