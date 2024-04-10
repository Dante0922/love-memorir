package com.lovememoir.server.api.controller.member;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.member.request.MemberCreateRequest;
import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.controller.member.response.MemberModifyResponse;
import com.lovememoir.server.api.controller.member.response.MemberRemoveResponse;
import com.lovememoir.server.api.service.member.MemberService;
import com.lovememoir.server.common.auth.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> createMember(@Valid @RequestBody MemberCreateRequest request) {

        String providerId = SecurityUtils.getProviderId();
        MemberCreateResponse response = memberService.createMember(request.toServiceRequest(providerId));
        return ApiResponse.created(response);
    }

    @PatchMapping()
    public ApiResponse<MemberModifyResponse> modifyMember(@Valid @RequestBody MemberModifyRequest request) {
        String providerId = SecurityUtils.getProviderId();

        MemberModifyResponse response = memberService.modifyMember(request.toServiceRequest(providerId));
        return ApiResponse.ok(response);
    }

    @DeleteMapping()
    public ApiResponse<MemberRemoveResponse> removeMember() {
        String providerId = SecurityUtils.getProviderId();
        MemberRemoveResponse response = memberService.removeMember(providerId);
        return ApiResponse.ok(response);
    }


}
