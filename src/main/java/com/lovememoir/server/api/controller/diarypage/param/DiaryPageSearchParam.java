package com.lovememoir.server.api.controller.diarypage.param;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryPageSearchParam {

    @Positive
    private Integer page = 1;

    @Builder
    public DiaryPageSearchParam(Integer page) {
        this.page = page;
    }
}
