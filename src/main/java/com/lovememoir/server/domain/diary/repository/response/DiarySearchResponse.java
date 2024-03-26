package com.lovememoir.server.domain.diary.repository.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiarySearchResponse {

    private final Long diaryId;
    private final Boolean isMain;
    private final String title;
    private final int pageCount;
    private final LocalDate relationshipStartedDate;

    @Builder
    public DiarySearchResponse(Long diaryId, Boolean isMain, String title, int pageCount, LocalDate relationshipStartedDate) {
        this.diaryId = diaryId;
        this.isMain = isMain;
        this.title = title;
        this.pageCount = pageCount;
        this.relationshipStartedDate = relationshipStartedDate;
    }
}
