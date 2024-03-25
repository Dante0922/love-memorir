package com.lovememoir.server.api.controller.member;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.member.request.MemberCreateRequest;
import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.controller.member.response.MemberModifyResponse;
import com.lovememoir.server.api.controller.member.response.MemberRemoveResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> createMember(@Valid @RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = MemberCreateResponse.builder()
                .memberKey("hello")
                .nickname(request.getNickname())
                .build();
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
