package com.lovememoir.server.api.controller.diarypage.response;

import com.lovememoir.server.domain.diarypage.DiaryPage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DiaryPageModifyResponse {

    private final Long diaryPageId;
    private final String title;
    private final int contentLength;
    private final LocalDate recordDate;
    private final LocalDateTime modifiedDateTime;


    @Builder
    private DiaryPageModifyResponse(Long diaryPageId, String title, int contentLength, LocalDate recordDate, LocalDateTime modifiedDateTime) {
        this.diaryPageId = diaryPageId;
        this.title = title;
        this.contentLength = contentLength;
        this.recordDate = recordDate;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static DiaryPageModifyResponse of(DiaryPage diaryPage) {
        return DiaryPageModifyResponse.builder()
            .diaryPageId(diaryPage.getId())
            .title(diaryPage.getTitle())
            .contentLength(diaryPage.getContent().length())
            .recordDate(diaryPage.getRecordDate())
            .modifiedDateTime(diaryPage.getLastModifiedDateTime())
            .build();
    }
}
