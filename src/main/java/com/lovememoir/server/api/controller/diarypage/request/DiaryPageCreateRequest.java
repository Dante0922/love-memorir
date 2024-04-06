package com.lovememoir.server.api.controller.diarypage.request;

import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.lovememoir.server.common.message.ValidationMessage.*;

@Getter
@Setter
@NoArgsConstructor
public class DiaryPageCreateRequest {

    @NotBlank(message = NOT_BLANK_DIARY_PAGE_TITLE)
    private String title;

    @NotBlank(message = NOT_BLANK_DIARY_PAGE_CONTENT)
    private String content;

    @NotNull(message = NOT_NULL_DIARY_PAGE_DATE)
    private LocalDate recordDate;

    private List<MultipartFile> images = new ArrayList<>();

    @Builder
    private DiaryPageCreateRequest(String title, String content, LocalDate recordDate, List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
        this.images = images;
    }

    public DiaryPageCreateServiceRequest toServiceRequest() {
        return DiaryPageCreateServiceRequest.builder()
            .title(title)
            .content(content)
            .recordDate(recordDate)
            .images(images)
            .build();
    }
}
