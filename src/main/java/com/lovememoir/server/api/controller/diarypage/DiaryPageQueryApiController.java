package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.api.controller.diarypage.param.DiaryPageSearchParam;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageResponse;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping
    public ApiResponse<SliceResponse<DiaryPagesResponse>> searchDiaryPages(
        @PathVariable Long diaryId,
        @Valid @ModelAttribute DiaryPageSearchParam param
    ) {
        DiaryPagesResponse response1 = DiaryPagesResponse.builder()
            .diaryPageId(1L)
            .pageTitle("푸바오와 마지막 인사")
            .build();
        DiaryPagesResponse response2 = DiaryPagesResponse.builder()
            .diaryPageId(2L)
            .pageTitle("루이바오의 먹방")
            .build();
        DiaryPagesResponse response3 = DiaryPagesResponse.builder()
            .diaryPageId(3L)
            .pageTitle("후쪽이 후이바오")
            .build();
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
        SliceResponse<DiaryPagesResponse> response = SliceResponse.of(List.of(response3, response2, response1), pageRequest, false);
        return ApiResponse.ok(response);
    }

    @GetMapping("/{diaryPageId}")
    public ApiResponse<DiaryPageResponse> searchDiaryPage(
        @PathVariable Long diaryId,
        @PathVariable Long diaryPageId
    ) {
        DiaryPageResponse response = DiaryPageResponse.builder()
            .diaryPageId(1L)
            .pageTitle("엄마 음식 훔쳐간 후이바오")
            .pageContent("후이바오는 엄마의 음식을 훔치는 것을 좋아합니다ㅋㅋㅋ")
            .diaryDate(LocalDate.of(2024, 3, 5))
            .dateTimeOfCreation(LocalDateTime.of(2024, 3, 21, 17, 22))
            .build();
        return ApiResponse.ok(response);
    }

}
