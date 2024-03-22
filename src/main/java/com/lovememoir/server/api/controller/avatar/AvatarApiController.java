package com.lovememoir.server.api.controller.avatar;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.avatar.request.AvatarCreateRequest;
import com.lovememoir.server.api.controller.avatar.response.AvatarCreateResponse;
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
}
