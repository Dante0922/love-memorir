package com.lovememoir.server.api.controller.diary;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.diary.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryImageModifyRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryModifyRequest;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.DiaryService;
import com.lovememoir.server.common.auth.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Diary Command Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries")
@Slf4j
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiaryCreateResponse> createDiary(@Valid @RequestBody DiaryCreateRequest request) {
        String providerId = SecurityUtils.getProviderId();

        LocalDate currentDate = LocalDate.now();

        DiaryCreateResponse response = diaryService.createDiary(providerId, currentDate, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{diaryId}")
    public ApiResponse<DiaryModifyResponse> modifyDiary(
        @PathVariable Long diaryId,
        @Valid @RequestBody DiaryModifyRequest request
    ) {
        String providerId = SecurityUtils.getProviderId();

        LocalDate currentDate = LocalDate.now();

        DiaryModifyResponse response = diaryService.modifyDiary(providerId, diaryId, currentDate, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/{diaryId}")
    public ApiResponse<DiaryModifyResponse> modifyDiaryProfile(
        @PathVariable Long diaryId,
        @Valid @ModelAttribute DiaryImageModifyRequest request
    ) {
        String providerId = SecurityUtils.getProviderId();

        DiaryModifyResponse response = diaryService.modifyDiaryProfile(providerId, diaryId, request.getProfile());

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{diaryId}")
    public ApiResponse<DiaryRemoveResponse> removeDiary(@PathVariable Long diaryId) {
        //TODO: 2024-03-26 10:30 dong82 회원 정보 토큰 추출
        String memberKey = UUID.randomUUID().toString();

        DiaryRemoveResponse response = diaryService.removeDiary(memberKey, diaryId);

        return ApiResponse.ok(response);
    }
}