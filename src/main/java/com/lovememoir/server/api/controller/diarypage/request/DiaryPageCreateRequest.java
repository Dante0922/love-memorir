package com.lovememoir.server.api.controller.diarypage.request;

import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.*;

@Getter
@NoArgsConstructor
public class DiaryPageCreateRequest {

    @NotBlank(message = NOT_BLANK_DIARY_PAGE_TITLE)
    private String title;

    @NotBlank(message = NOT_BLANK_DIARY_PAGE_CONTENT)
    private String content;

    @NotNull(message = NOT_BLANK_DIARY_PAGE_DATE)
    private LocalDate diaryDate;

    @Builder
    private DiaryPageCreateRequest(String title, String content, LocalDate diaryDate) {
        this.title = title;
        this.content = content;
        this.diaryDate = diaryDate;
    }

    public DiaryPageCreateServiceRequest toServiceRequest() {
        return DiaryPageCreateServiceRequest.builder()
            .title(title)
            .content(content)
            .diaryDate(diaryDate)
            .build();
    }
}
