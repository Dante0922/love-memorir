package com.lovememoir.server.api.controller.diary;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.ListResponse;
import com.lovememoir.server.api.service.diary.DiaryQueryService;
import com.lovememoir.server.common.auth.SecurityUtils;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Diary Query Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryQueryApiController {

    private final DiaryQueryService diaryQueryService;

    @GetMapping
    public ApiResponse<ListResponse<DiarySearchResponse>> searchDiaries() {
        String providerId = SecurityUtils.getProviderId();

        List<DiarySearchResponse> content = diaryQueryService.searchDiaries(providerId);

        ListResponse<DiarySearchResponse> response = ListResponse.of(content);

        return ApiResponse.ok(response);
    }
}
