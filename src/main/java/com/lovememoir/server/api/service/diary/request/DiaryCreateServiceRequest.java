package com.lovememoir.server.api.service.diary.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiaryCreateServiceRequest {

    private final String title;
    private final LocalDate relationshipStartedDate;

    @Builder
    private DiaryCreateServiceRequest(String title, LocalDate relationshipStartedDate) {
        this.title = title;
        this.relationshipStartedDate = relationshipStartedDate;
    }
}
