package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageCreateRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageModifyRequest;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageModifyResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageRemoveResponse;
import com.lovememoir.server.api.service.diarypage.DiaryPageService;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageResponse;
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
        //TODO: 2024-03-26 17:49 dong82 회원 정보 토큰 추출
        String memberKey = UUID.randomUUID().toString();

        LocalDateTime currentDateTime = LocalDateTime.now();

        DiaryPageModifyResponse response = diaryPageService.modifyDiaryPage(memberKey, diaryPageId, currentDateTime, request.toServiceRequest());

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
