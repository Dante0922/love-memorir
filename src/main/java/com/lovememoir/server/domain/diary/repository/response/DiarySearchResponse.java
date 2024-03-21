package com.lovememoir.server.domain.diary.repository.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiarySearchResponse {

    private final Long diaryId;
    private final String title;
    private final LocalDate startDiary;

    @Builder
    public DiarySearchResponse(Long diaryId, String title, LocalDate startDiary) {
        this.diaryId = diaryId;
        this.title = title;
        this.startDiary = startDiary;
    }
}
