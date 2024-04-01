package com.lovememoir.server.api.controller.diary;

import com.lovememoir.server.api.ApiResponse;
import com.lovememoir.server.api.controller.diary.request.DiaryCreateRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryImageModifyRequest;
import com.lovememoir.server.api.controller.diary.request.DiaryModifyRequest;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.DiaryService;
import com.lovememoir.server.common.auth.CurrentMember;
import com.lovememoir.server.common.auth.jwt.CustomUser;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Diary Command Api Controller
 *
 * @author dong82
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/diaries")
@Slf4j
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiaryCreateResponse> createDiary(@Valid @RequestBody DiaryCreateRequest request,
                                                        @CurrentMember Member member) {
        //TODO: 2024-03-26 00:53 dong82 회원 정보 토큰 추출

//        log.info("member : {}", member.getId());
//        log.info("member : {}", member.getMemberKey());
//        log.info("member : {}", member.getNickname());
//        log.info("member : {}", member.getBirth());
//        log.info("member : {}", member.getGender());
//        log.info("member : {}", member.getAuth());
//        log.info("member : {}", member.getAuth().getProvider());
//        log.info("member : {}", member.getAuth().getProviderId());


        String memberKey = UUID.randomUUID().toString();

        LocalDateTime currentDateTime = LocalDateTime.now();

        DiaryCreateResponse response = diaryService.createDiary(memberKey, currentDateTime, request.toServiceRequest());
        // TODO 테스트코드 500 떨어지길래 임시로 막아두었어요. 확인 부탁드려요.
        return ApiResponse.created(response);
    }

    @PatchMapping("/{diaryId}")
    public ApiResponse<DiaryModifyResponse> modifyDiary(
        @PathVariable Long diaryId,
        @Valid @RequestBody DiaryModifyRequest request
    ) {
        //TODO: 2024-03-26 02:19 dong82 회원 정보 토큰 추출
        String memberKey = UUID.randomUUID().toString();

        LocalDateTime currentDateTime = LocalDateTime.now();

        DiaryModifyResponse response = diaryService.modifyDiary(memberKey, diaryId, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/{diaryId}")
    public ApiResponse<DiaryModifyResponse> modifyDiaryImage(
        @PathVariable Long diaryId,
        @Valid @ModelAttribute DiaryImageModifyRequest request
    ) {
        //TODO: 2024-03-27 00:23 dong82 회원 정보 토큰 추출
        String memberKey = UUID.randomUUID().toString();

        DiaryModifyResponse response = diaryService.modifyDiaryImage(memberKey, diaryId, request.getProfile());

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{diaryId}")
    public ApiResponse<DiaryRemoveResponse> removeDiary(@PathVariable Long diaryId) {
        //TODO: 2024-03-26 10:30 dong82 회원 정보 토큰 추출
        String memberKey = UUID.randomUUID().toString();

        DiaryRemoveResponse response = diaryService.removeDiary(memberKey, diaryId);

        return ApiResponse.ok(response);
    }
}