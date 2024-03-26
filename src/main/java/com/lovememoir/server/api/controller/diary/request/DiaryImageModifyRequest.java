package com.lovememoir.server.api.controller.diary.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class DiaryImageModifyRequest {

    private MultipartFile profile;

    @Builder
    private DiaryImageModifyRequest(MultipartFile profile) {
        this.profile = profile;
    }
}
