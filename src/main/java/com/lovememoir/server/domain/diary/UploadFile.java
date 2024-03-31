package com.lovememoir.server.domain.diary;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class UploadFile {

    @Column(updatable = false, length = 200)
    private final String uploadFileName;

    @Column(updatable = false, length = 200)
    private final String storeFileUrl;

    @Builder
    private UploadFile(String uploadFileName, String storeFileUrl) {
        this.uploadFileName = uploadFileName;
        this.storeFileUrl = storeFileUrl;
    }
}
