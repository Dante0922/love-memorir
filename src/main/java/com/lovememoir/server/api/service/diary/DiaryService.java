package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    public DiaryCreateResponse createDiary(final String providerId, final LocalDateTime currentDateTime, DiaryCreateServiceRequest request) {
        return null;
    }

    public DiaryModifyResponse modifyDiary(final String memberKey, final Long diaryId, final LocalDateTime currentDateTime, DiaryModifyServiceRequest request) {
        return null;
    }

    public DiaryModifyResponse modifyDiaryImage(final String memberKey, final Long diaryId, final MultipartFile file) {
        return null;
    }

    public DiaryRemoveResponse removeDiary(final String memberKey, final Long diaryId) {
        return null;
    }
}
