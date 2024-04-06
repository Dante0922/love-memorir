package com.lovememoir.server.api.controller.member;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.member.response.MemberResponse;
import com.lovememoir.server.api.service.member.MemberQueryService;
import com.lovememoir.server.common.auth.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberQueryApiController {

    private final MemberQueryService memberQueryService;

    @GetMapping
    public ApiResponse<MemberResponse> searchMember() {

        String providerId = SecurityUtils.getProviderId();
        MemberResponse response = memberQueryService.searchMember(providerId);
        return ApiResponse.ok(response);
    }
}
