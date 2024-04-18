package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.domain.avatar.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/avatars")
public class AvatarApiController {

    @GetMapping("/refresh")
    public ApiResponse<AvatarRefreshResponse> refreshAvatar() {
        AvatarRefreshResponse response = AvatarRefreshResponse.builder()
                .emotion(Emotion.STABILITY)
                .question("오늘은 무슨 일이 있었나요?")
                .build();
        return ApiResponse.ok(response);
    }
}
