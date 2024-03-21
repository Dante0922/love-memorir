package com.lovememoir.server.api.controller.diary;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.ListResponse;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Diary Query Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryQueryApiController {

    @GetMapping
    public ApiResponse<ListResponse<DiarySearchResponse>> searchDiaries() {
        DiarySearchResponse response1 = DiarySearchResponse.builder()
            .diaryId(1L)
            .title("푸바오")
            .startDiary(LocalDate.of(2020, 7, 20))
            .build();
        DiarySearchResponse response2 = DiarySearchResponse.builder()
            .diaryId(2L)
            .title("루이바오")
            .startDiary(LocalDate.of(2020, 7, 7))
            .build();
        DiarySearchResponse response3 = DiarySearchResponse.builder()
            .diaryId(3L)
            .title("후이바오")
            .startDiary(LocalDate.of(2020, 7, 7))
            .build();
        ListResponse<DiarySearchResponse> response = ListResponse.of(List.of(response3, response2, response1));
        return ApiResponse.ok(response);
    }
}
