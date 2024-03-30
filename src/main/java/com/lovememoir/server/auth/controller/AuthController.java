package com.lovememoir.server.auth.controller;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.auth.dto.AuthRequest;
import com.lovememoir.server.auth.dto.AuthResponse;
import com.lovememoir.server.auth.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoAuthService kakaoAuthService;

    @RequestMapping("/kakao")
    public ApiResponse<AuthResponse> kakaoAuth(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(kakaoAuthService.login(authRequest));
    }

}
