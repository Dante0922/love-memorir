package com.lovememoir.server.api.controller;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.response.DiaryCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiaryCreateResponse> createDiary(@Valid @RequestBody DiaryCreateRequest request) {
        DiaryCreateResponse response = DiaryCreateResponse.builder()
            .diaryId(1L)
            .title("푸바오")
            .createdDateTime(LocalDateTime.of(2024, 1, 1, 0, 0))
            .build();

        return ApiResponse.created(response);
    }
}