package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageModifyResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageRemoveResponse;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.lovememoir.server.api.service.diarypage.DiaryPageValidator.validateDiaryDate;
import static com.lovememoir.server.api.service.diarypage.DiaryPageValidator.validateTitle;
import static com.lovememoir.server.common.message.ExceptionMessage.*;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryPageService {

    private final DiaryPageRepository diaryPageRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    public DiaryPageCreateResponse createDiaryPage(final String memberKey, final Long diaryId, final LocalDateTime currentDateTime, DiaryPageCreateServiceRequest request) {
        return null;
    }

    public DiaryPageModifyResponse modifyDiaryPage(final String memberKey, final Long diaryPageId, final LocalDateTime currentDateTime, DiaryPageModifyServiceRequest request) {
        return null;
    }

    public DiaryPageRemoveResponse removeDiaryPage(final List<Long> diaryPageId) {
        return null;
    }
}
