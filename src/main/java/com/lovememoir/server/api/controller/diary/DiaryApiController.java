package com.lovememoir.server.api.controller.diary;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.diary.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryModifyRequest;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Diary Command Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiaryCreateResponse> createDiary(@Valid @RequestBody DiaryCreateRequest request) {
        String memberKey = UUID.randomUUID().toString();

        LocalDateTime currentDateTime = LocalDateTime.now();

        DiaryCreateResponse response = diaryService.createDiary(memberKey, currentDateTime, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{diaryId}")
    public ApiResponse<DiaryModifyResponse> modifyDiary(
        @PathVariable Long diaryId,
        @Valid @RequestBody DiaryModifyRequest request
    ) {
        DiaryModifyResponse response = DiaryModifyResponse.builder()
            .diaryId(1L)
            .title("루이바오")
            .build();

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{diaryId}")
    public ApiResponse<DiaryRemoveResponse> removeDiary(@PathVariable Long diaryId) {
        DiaryRemoveResponse response = DiaryRemoveResponse.builder()
            .diaryId(1L)
            .title("후이바오")
            .build();

        return ApiResponse.ok(response);
    }
}