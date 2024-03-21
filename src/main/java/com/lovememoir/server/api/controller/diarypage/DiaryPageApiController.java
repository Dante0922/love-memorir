package com.lovememoir.server.api.controller.diarypage;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.diarypage.request.DiaryPageCreateRequest;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Diary Page Command Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries/{diaryId}/pages")
public class DiaryPageApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiaryPageCreateResponse> createDiaryPage(
        @PathVariable Long diaryId,
        @Valid @RequestBody DiaryPageCreateRequest request
    ) {
        DiaryPageCreateResponse response = DiaryPageCreateResponse.builder()
            .diaryPageId(1L)
            .title("푸바오가 떠나는 날")
            .contentLength("푸바오를 볼 수 있는 마지막날 에버랜드에서 오픈런했다.".length())
            .diaryDate(LocalDate.of(2024, 3, 3))
            .createdDateTime(LocalDateTime.of(2024, 3, 5, 16, 0))
            .build();

        return ApiResponse.created(response);
    }
}
