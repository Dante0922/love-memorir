package com.lovememoir.server.api.service.diarypage.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiaryPageCreateServiceRequest {

    private final String title;
    private final String content;
    private final LocalDate diaryDate;

    @Builder
    private DiaryPageCreateServiceRequest(String title, String content, LocalDate diaryDate) {
        this.title = title;
        this.content = content;
        this.diaryDate = diaryDate;
    }
}
