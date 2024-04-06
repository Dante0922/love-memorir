package com.lovememoir.server.api.controller.diary.request;

import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_DIARY_TITLE;
import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_IS_LOVE;

@Getter
@NoArgsConstructor
public class DiaryCreateRequest {

    @NotBlank(message = NOT_BLANK_DIARY_TITLE)
    private String title;

    @NotNull(message = NOT_NULL_IS_LOVE)
    private Boolean isLove;

    private LocalDate startedDate;

    private LocalDate finishedDate;

    @Builder
    private DiaryCreateRequest(String title, Boolean isLove, LocalDate startedDate, LocalDate finishedDate) {
        this.title = title;
        this.isLove = isLove;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
    }

    public DiaryCreateServiceRequest toServiceRequest() {
        return DiaryCreateServiceRequest.builder()
            .title(title)
            .isLove(isLove)
            .startedDate(startedDate)
            .finishedDate(finishedDate)
            .build();
    }
}
