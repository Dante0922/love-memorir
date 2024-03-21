package com.lovememoir.server.api.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.lovememoir.server.common.message.ValidationMessage.NOT_BLANK_DIARY_TITLE;

@Getter
@NoArgsConstructor
public class DiaryCreateRequest {

    @NotBlank(message = NOT_BLANK_DIARY_TITLE)
    private String title;

    @Builder
    private DiaryCreateRequest(String title) {
        this.title = title;
    }
}
