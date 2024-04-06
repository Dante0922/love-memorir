package com.lovememoir.server.api.controller.diary.response;

import com.lovememoir.server.domain.diary.Diary;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiaryRemoveResponse {

    private final long diaryId;
    private final String title;
    private final Boolean isLove;
    private final LocalDate startedDate;
    private final LocalDate finishedDate;

    @Builder
    private DiaryRemoveResponse(long diaryId, String title, Boolean isLove, LocalDate startedDate, LocalDate finishedDate) {
        this.diaryId = diaryId;
        this.title = title;
        this.isLove = isLove;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
    }

    public static DiaryRemoveResponse of(Diary diary) {
        return DiaryRemoveResponse.builder()
            .diaryId(diary.getId())
            .title(diary.getTitle())
            .isLove(diary.getLoveInfo().isLove())
            .startedDate(diary.getLoveInfo().getStartedDate())
            .finishedDate(diary.getLoveInfo().getFinishedDate())
            .build();
    }
}
