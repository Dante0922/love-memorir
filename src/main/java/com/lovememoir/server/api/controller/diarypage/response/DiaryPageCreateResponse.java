package com.lovememoir.server.api.controller.diarypage.response;

import com.lovememoir.server.domain.attachedimage.AttachedImage;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DiaryPageCreateResponse {

    private final Long diaryPageId;
    private final String title;
    private final int contentLength;
    private final LocalDate recordDate;
    private final int imageCount;
    private final LocalDateTime createdDateTime;

    @Builder
    private DiaryPageCreateResponse(Long diaryPageId, String title, int contentLength, LocalDate recordDate, int imageCount, LocalDateTime createdDateTime) {
        this.diaryPageId = diaryPageId;
        this.title = title;
        this.contentLength = contentLength;
        this.recordDate = recordDate;
        this.imageCount = imageCount;
        this.createdDateTime = createdDateTime;
    }

    public static DiaryPageCreateResponse of(DiaryPage diaryPage, List<AttachedImage> images) {
        return DiaryPageCreateResponse.builder()
            .diaryPageId(diaryPage.getId())
            .title(diaryPage.getTitle())
            .contentLength(diaryPage.getContent().length())
            .recordDate(diaryPage.getRecordDate())
            .imageCount(images.size())
            .createdDateTime(diaryPage.getCreatedDateTime())
            .build();
    }
}
