package com.lovememoir.server.api.controller.diary.response;

import com.lovememoir.server.domain.diary.Diary;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiaryModifyResponse {

    private final long diaryId;
    private final String title;
    private final Boolean isLove;
    private final LocalDate startedDate;
    private final LocalDate finishedDate;

    @Builder
    private DiaryModifyResponse(Long diaryId, String title, Boolean isLove, LocalDate startedDate, LocalDate finishedDate) {
        this.diaryId = diaryId;
        this.title = title;
        this.isLove = isLove;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
    }

    public static DiaryModifyResponse of(Diary diary) {
        return DiaryModifyResponse.builder()
            .diaryId(diary.getId())
            .title(diary.getTitle())
            .isLove(diary.getLoveInfo().isLove())
            .startedDate(diary.getLoveInfo().getStartedDate())
            .finishedDate(diary.getLoveInfo().getFinishedDate())
            .build();
    }
}
