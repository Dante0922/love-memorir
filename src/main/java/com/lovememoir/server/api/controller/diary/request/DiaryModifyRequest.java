package com.lovememoir.server.api.controller.diary.request;

import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
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
public class DiaryModifyRequest {

    @NotBlank(message = NOT_BLANK_DIARY_TITLE)
    private String title;

    @NotNull(message = NOT_NULL_IS_LOVE)
    private Boolean isInLove;

    private LocalDate relationshipStartedDate;

    @Builder
    private DiaryModifyRequest(String title, Boolean isInLove, LocalDate relationshipStartedDate) {
        this.title = title;
        this.isInLove = isInLove;
        this.relationshipStartedDate = relationshipStartedDate;
    }

    public DiaryModifyServiceRequest toServiceRequest() {
        return DiaryModifyServiceRequest.builder()
            .title(title)
            .isInLove(isInLove)
            .relationshipStartedDate(relationshipStartedDate)
            .build();
    }
}
