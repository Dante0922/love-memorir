package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.api.service.avatar.AvatarService;
import com.lovememoir.server.common.auth.SecurityUtils;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/avatars")
public class AvatarApiController {

    private final AvatarService avatarService;

    @GetMapping("/refresh")
    public ApiResponse<AvatarRefreshResponse> refreshAvatar() {

        String providerId = SecurityUtils.getProviderId();

        AvatarRefreshResponse response = avatarService.refreshAvatar(providerId);
        return ApiResponse.ok(response);
    }
}
