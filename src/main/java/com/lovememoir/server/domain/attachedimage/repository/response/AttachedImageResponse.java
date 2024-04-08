package com.lovememoir.server.domain.attachedimage.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class AttachedImageResponse {

    private final long imageId;
    private final String url;

    @Builder
    private AttachedImageResponse(long imageId, String url) {
        this.imageId = imageId;
        this.url = url;
    }
}
