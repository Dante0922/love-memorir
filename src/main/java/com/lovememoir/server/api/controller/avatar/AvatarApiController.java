package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.api.service.avatar.AvatarService;
import com.lovememoir.server.common.auth.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/avatars")
public class AvatarApiController {

    private final AvatarService avatarService;

    @PostMapping("/refresh")
    public ApiResponse<AvatarRefreshResponse> refreshAvatar() {

        String providerId = SecurityUtils.getProviderId();

        AvatarRefreshResponse response = avatarService.refreshAvatar(providerId);
        return ApiResponse.ok(response);
    }
}
