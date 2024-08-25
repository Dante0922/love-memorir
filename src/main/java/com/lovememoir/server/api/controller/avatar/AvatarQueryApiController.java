package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.service.avatar.AvatarQueryService;
import com.lovememoir.server.common.auth.SecurityUtils;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/avatars")
public class AvatarQueryApiController {

    private final AvatarQueryService avatarQueryService;

    @GetMapping
    public ApiResponse<AvatarResponse> searchAvatar() {

        String providerId = SecurityUtils.getProviderId();
        AvatarResponse response = avatarQueryService.searchAvatar(providerId);

        return ApiResponse.ok(response);
    }
}
