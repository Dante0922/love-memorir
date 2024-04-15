package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/avatars")
public class AvatarQueryApiController {

    @GetMapping()
    public ApiResponse<AvatarResponse> searchAvatar() {
        AvatarResponse response = AvatarResponse.builder()
                .emotion("W@")
                .question("오늘은 무슨 일이 있었나요?")
                .build();
        return ApiResponse.ok(response);
    }
}
