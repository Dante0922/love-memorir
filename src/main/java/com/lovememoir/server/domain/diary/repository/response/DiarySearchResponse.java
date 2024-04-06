package com.lovememoir.server.domain.diary.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(force = true)
public class DiarySearchResponse {

    private final long diaryId;
    private final Boolean isMain;
    private final String title;
    private final Boolean isLove;
    private final LocalDate startedDate;
    private final LocalDate finishedDate;
    private final String profileImage;

    @Builder
    private DiarySearchResponse(long diaryId, Boolean isMain, String title, Boolean isLove, LocalDate startedDate, LocalDate finishedDate, String profileImage) {
        this.diaryId = diaryId;
        this.isMain = isMain;
        this.title = title;
        this.isLove = isLove;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
        this.profileImage = profileImage;
    }
}
