package com.lovememoir.server.api.controller.diarypage.response;

import com.lovememoir.server.domain.diarypage.DiaryPage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DiaryPageModifyResponse {

    private final Long diaryPageId;
    private final String title;
    private final int contentLength;
    private final LocalDate diaryDate;

    @Builder
    private DiaryPageModifyResponse(Long diaryPageId, String title, int contentLength, LocalDate diaryDate) {
        this.diaryPageId = diaryPageId;
        this.title = title;
        this.contentLength = contentLength;
        this.diaryDate = diaryDate;
    }

    public static DiaryPageModifyResponse of(DiaryPage diaryPage) {
        return DiaryPageModifyResponse.builder()
            .diaryPageId(diaryPage.getId())
            .title(diaryPage.getTitle())
            .contentLength(diaryPage.getContent().length())
            .diaryDate(diaryPage.getDiaryDate())
            .build();
    }
}
