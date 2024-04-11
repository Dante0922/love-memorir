package com.lovememoir.server.api.controller.auth;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.auth.request.AuthRequest;
import com.lovememoir.server.api.controller.auth.response.AuthResponse;
import com.lovememoir.server.api.service.auth.AppleAuthService;
import com.lovememoir.server.api.service.auth.GoogleAuthService;
import com.lovememoir.server.api.service.auth.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthApiController {

    private final KakaoAuthService kakaoAuthService;
    private final AppleAuthService appleAuthService;
    private final GoogleAuthService googleAuthService;

    @RequestMapping("/kakao")
    public ApiResponse<AuthResponse> kakaoAuth(@RequestBody AuthRequest authRequest) {
        log.info("authRequest: {}", authRequest);
        return ApiResponse.success(kakaoAuthService.login(authRequest));
    }

    @RequestMapping("/apple")
    public ApiResponse<AuthResponse> appleAuth(@RequestBody AuthRequest authRequest) {
        log.info("authRequest: {}", authRequest);
        return ApiResponse.success(appleAuthService.login(authRequest));
    }

    @RequestMapping("/google")
    public ApiResponse<AuthResponse> googleAuth(@RequestBody AuthRequest authRequest) {
        log.info("authRequest: {}", authRequest);
        return ApiResponse.success(googleAuthService.login(authRequest));
    }
}
