package com.lovememoir.server.api.service.diary.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiaryCreateServiceRequest {

    private final String title;
    private final boolean isLove;
    private final LocalDate startedDate;
    private final LocalDate finishedDate;

    @Builder
    private DiaryCreateServiceRequest(String title, boolean isLove, LocalDate startedDate, LocalDate finishedDate) {
        this.title = title;
        this.isLove = isLove;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
    }
}
