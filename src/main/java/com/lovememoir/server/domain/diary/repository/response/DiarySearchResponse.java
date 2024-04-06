package com.lovememoir.server.domain.diary.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(force = true)
public class DiarySearchResponse {

    private final long diaryId;
    private final String title;
    private final Boolean isLove;
    private final LocalDate startDate;
    private final LocalDate finishDate;
    private final String profileImage;

    @Builder
    private DiarySearchResponse(long diaryId, String title, Boolean isLove, LocalDate startDate, LocalDate finishDate, String profileImage) {
        this.diaryId = diaryId;
        this.title = title;
        this.isLove = isLove;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.profileImage = profileImage;
    }
}
