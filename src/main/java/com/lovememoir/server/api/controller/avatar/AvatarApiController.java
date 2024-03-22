package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.avatar.request.AvatarCreateRequest;
import com.lovememoir.server.api.controller.avatar.request.AvatarModifyRequest;
import com.lovememoir.server.api.controller.avatar.response.AvatarCreateResponse;
import com.lovememoir.server.api.controller.avatar.response.AvatarModifyResponse;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/avatars")
public class AvatarApiController {

    @PostMapping("/{memberId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AvatarCreateResponse> createAvatar(@PathVariable Long memberId, @Valid @RequestBody AvatarCreateRequest request) {
        AvatarCreateResponse response = AvatarCreateResponse.builder()
                .memberId(memberId)
                .avatarType(1)
                .build();
        return ApiResponse.created(response);
    }

    @PatchMapping("/{memberId}")
    public ApiResponse<AvatarModifyResponse> modifyAvatar(@PathVariable Long memberId, @Valid @RequestBody AvatarModifyRequest request) {
        AvatarModifyResponse response = AvatarModifyResponse.builder()
                .avatarType(1)
                .growthStage(1)
                .build();
        return ApiResponse.ok(response);
    }

    @GetMapping("/{memberId}/refresh")
    public ApiResponse<AvatarRefreshResponse> refreshAvatar(@PathVariable Long memberId) {
        AvatarRefreshResponse response = AvatarRefreshResponse.builder()
                .behavior(2)
                .question("오늘은 무슨 일이 있었나요?")
                .build();
        return ApiResponse.ok(response);
    }
}
