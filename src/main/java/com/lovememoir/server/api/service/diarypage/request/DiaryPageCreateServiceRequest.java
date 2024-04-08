package com.lovememoir.server.api.service.diarypage.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
public class DiaryPageCreateServiceRequest {

    private final String title;
    private final String content;
    private final LocalDate recordDate;
    private final List<MultipartFile> images;

    @Builder
    private DiaryPageCreateServiceRequest(String title, String content, LocalDate recordDate, List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
        this.images = images;
    }
}
