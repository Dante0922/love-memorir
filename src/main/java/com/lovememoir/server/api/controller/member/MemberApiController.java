package com.lovememoir.server.api.controller.member;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.member.request.MemberCreateRequest;
import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.controller.member.response.MemberModifyResponse;
import com.lovememoir.server.api.controller.member.response.MemberRemoveResponse;
import com.lovememoir.server.api.service.member.MemberService;
import com.lovememoir.server.api.service.member.request.MemberCreateServiceRequest;
import com.lovememoir.server.common.auth.jwt.CustomUser;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> createMember(@Valid @RequestBody MemberCreateRequest request
    , @AuthenticationPrincipal UserDetails userDetails) {
        String providerId = userDetails.getUsername();
        MemberCreateResponse response = memberService.createMember(request.toServiceRequest(providerId));
        return ApiResponse.created(response);
    }

    @PatchMapping()
    public ApiResponse<MemberModifyResponse> modifyMember(@Valid @RequestBody MemberModifyRequest request) {
        MemberModifyResponse response = MemberModifyResponse.builder()
            .memberKey("hello")
            .nickname(request.getNickname())
            .birth(request.getBirth())
            .gender("F")
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping()
    public ApiResponse<MemberRemoveResponse> removeMember() {
        MemberRemoveResponse response = MemberRemoveResponse.builder()
            .memberKey("hello")
            .nickname("잘가세요")
            .build();
        return ApiResponse.ok(response);
    }


}
