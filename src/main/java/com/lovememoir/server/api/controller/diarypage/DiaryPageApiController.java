package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageCreateRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageModifyRequest;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageRemoveRequest;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageModifyResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageRemoveResponse;
import com.lovememoir.server.api.service.diarypage.DiaryPageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/delete")
    public ApiResponse<DiaryPageRemoveResponse> removeDiaryPage(@PathVariable Long diaryId, @RequestBody DiaryPageRemoveRequest request) {
        DiaryPageRemoveResponse response = diaryPageService.removeDiaryPage(request.getDiaryPageIds());

        return ApiResponse.ok(response);
    }
}
