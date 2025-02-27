package com.lovememoir.server.api.controller.diarypage.request;

import com.lovememoir.server.api.service.diarypage.request.DiaryPageModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.*;

@Getter
@NoArgsConstructor
public class DiaryPageModifyRequest {

    @NotBlank(message = NOT_BLANK_DIARY_PAGE_TITLE)
    private String title;

    @NotBlank(message = NOT_BLANK_DIARY_PAGE_CONTENT)
    private String content;

    @NotNull(message = NOT_NULL_DIARY_PAGE_DATE)
    private LocalDate recordDate;

    @Builder
    private DiaryPageModifyRequest(String title, String content, LocalDate recordDate) {
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
    }

    public DiaryPageModifyServiceRequest toServiceRequest() {
        return DiaryPageModifyServiceRequest.builder()
            .title(title)
            .content(content)
            .recordDate(recordDate)
            .build();
    }
}
