package com.lovememoir.server.domain.diarypage.repository.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DiaryPageResponse {

    private final Long diaryPageId;
    private final String pageTitle;
    private final String pageContent;
    private final LocalDate diaryDate;
    private final LocalDateTime dateTimeOfCreation;

    @Builder
    public DiaryPageResponse(Long diaryPageId, String pageTitle, String pageContent, LocalDate diaryDate, LocalDateTime dateTimeOfCreation) {
        this.diaryPageId = diaryPageId;
        this.pageTitle = pageTitle;
        this.pageContent = pageContent;
        this.diaryDate = diaryDate;
        this.dateTimeOfCreation = dateTimeOfCreation;
    }
}
