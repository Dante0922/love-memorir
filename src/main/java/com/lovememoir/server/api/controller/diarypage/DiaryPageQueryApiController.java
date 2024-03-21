package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.api.controller.diarypage.param.DiaryPageSearchParam;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageSearchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Diary Page Query Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries/{diaryId}/pages")
public class DiaryPageQueryApiController {

    @GetMapping
    public ApiResponse<SliceResponse<DiaryPageSearchResponse>> searchDiaryPages(
        @PathVariable Long diaryId,
        @Valid @ModelAttribute DiaryPageSearchParam param
    ) {
        DiaryPageSearchResponse response1 = DiaryPageSearchResponse.builder()
            .diaryPageId(1L)
            .pageTitle("푸바오와 마지막 인사")
            .build();
        DiaryPageSearchResponse response2 = DiaryPageSearchResponse.builder()
            .diaryPageId(1L)
            .pageTitle("루이바오의 먹방")
            .build();
        DiaryPageSearchResponse response3 = DiaryPageSearchResponse.builder()
            .diaryPageId(1L)
            .pageTitle("후쪽이 후이바오")
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);
        SliceResponse<DiaryPageSearchResponse> response = SliceResponse.of(List.of(response3, response2, response1), pageRequest, false);
        return ApiResponse.ok(response);
    }
}
