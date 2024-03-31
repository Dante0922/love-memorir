package com.lovememoir.server.domain.diary.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(force = true)
public class DiarySearchResponse {

    private final Long diaryId;
    private final Boolean isMain;
    private final String title;
    private final String profileImage;
    private final int pageCount;
    private final LocalDate relationshipStartedDate;

    @Builder
    private DiarySearchResponse(Long diaryId, Boolean isMain, String title, String profileImage, int pageCount, LocalDate relationshipStartedDate) {
        this.diaryId = diaryId;
        this.isMain = isMain;
        this.title = title;
        this.profileImage = profileImage;
        this.pageCount = pageCount;
        this.relationshipStartedDate = relationshipStartedDate;
    }
}
