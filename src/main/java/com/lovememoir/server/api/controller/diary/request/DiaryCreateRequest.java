package com.lovememoir.server.api.controller.diary.request;

import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_DIARY_TITLE;
import static com.lovememoir.server.common.message.ValidationMessage.NOT_NULL_IS_IN_LOVE;

@Getter
@NoArgsConstructor
public class DiaryCreateRequest {

    @NotBlank(message = NOT_BLANK_DIARY_TITLE)
    private String title;

    @NotNull(message = NOT_NULL_IS_IN_LOVE)
    private Boolean isInLove;

    private LocalDate relationshipStartedDate;

    @Builder
    private DiaryCreateRequest(String title, Boolean isInLove, LocalDate relationshipStartedDate) {
        this.title = title;
        this.isInLove = isInLove;
        this.relationshipStartedDate = relationshipStartedDate;
    }

    public DiaryCreateServiceRequest toServiceRequest() {
        return null;
    }
}
