package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.api.controller.diarypage.param.DiaryPageSearchParam;
import com.lovememoir.server.api.service.diarypage.DiaryPageQueryService;
import com.lovememoir.server.api.service.diarypage.response.DiaryPageResponse;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static com.lovememoir.server.common.constant.GlobalConstant.PAGE_SIZE;

/**
 * Diary Page Query Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries/{diaryId}/pages")
public class DiaryPageQueryApiController {

    private final DiaryPageQueryService diaryPageQueryService;

    @GetMapping
    public ApiResponse<SliceResponse<DiaryPagesResponse>> searchDiaryPages(
        @PathVariable Long diaryId,
        @Valid @ModelAttribute DiaryPageSearchParam param
    ) {
        PageRequest pageRequest = PageRequest.of(param.getPage() - 1, PAGE_SIZE);

        SliceResponse<DiaryPagesResponse> response = diaryPageQueryService.searchDiaryPages(diaryId, pageRequest);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{diaryPageId}")
    public ApiResponse<DiaryPageResponse> searchDiaryPage(
        @PathVariable Long diaryId,
        @PathVariable Long diaryPageId
    ) {
        DiaryPageResponse response = diaryPageQueryService.searchDiaryPage(diaryPageId);

        return ApiResponse.ok(response);
    }

}
