package com.lovememoir.server.domain.diary.repository.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiarySearchResponse {

    private final Long diaryId;
    private final Boolean isMain;
    private final String title;
    private final int diaryPage;
    private final LocalDate relationshipStartedDate;

    @Builder
    public DiarySearchResponse(Long diaryId, Boolean isMain, String title, int diaryPage, LocalDate relationshipStartedDate) {
        this.diaryId = diaryId;
        this.isMain = isMain;
        this.title = title;
        this.diaryPage = diaryPage;
        this.relationshipStartedDate = relationshipStartedDate;
    }
}
