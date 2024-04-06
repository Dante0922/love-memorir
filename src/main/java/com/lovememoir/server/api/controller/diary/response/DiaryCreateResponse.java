package com.lovememoir.server.api.controller.diary.response;

import com.lovememoir.server.domain.diary.Diary;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DiaryCreateResponse {

    private final long diaryId;
    private final String title;
    private final Boolean isLove;
    private final LocalDate startedDate;
    private final LocalDate finishedDate;
    private final LocalDateTime createdDateTime;

    @Builder
    private DiaryCreateResponse(long diaryId, String title, Boolean isLove, LocalDate startedDate, LocalDate finishedDate, LocalDateTime createdDateTime) {
        this.diaryId = diaryId;
        this.title = title;
        this.isLove = isLove;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
        this.createdDateTime = createdDateTime;
    }

    public static DiaryCreateResponse of(Diary diary) {
        return DiaryCreateResponse.builder()
            .diaryId(diary.getId())
            .title(diary.getTitle())
            .isLove(diary.getLoveInfo().isLove())
            .startedDate(diary.getLoveInfo().getStartedDate())
            .finishedDate(diary.getLoveInfo().getFinishedDate())
            .createdDateTime(diary.getCreatedDateTime())
            .build();
    }
}
