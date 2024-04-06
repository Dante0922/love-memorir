package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.lovememoir.server.api.service.diary.DiaryValidator.validateLoveInfo;
import static com.lovememoir.server.api.service.diary.DiaryValidator.validateTitle;
import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_MEMBER;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    public DiaryCreateResponse createDiary(final String providerId, final LocalDate currentDate, DiaryCreateServiceRequest request) {
        String title = validateTitle(request.getTitle());
        LoveInfo loveInfo = validateLoveInfo(request.isLove(), request.getStartedDate(), request.getFinishedDate(), currentDate);

        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        Diary diary = Diary.create(title, loveInfo, member);
        Diary savedDiary = diaryRepository.save(diary);

        return DiaryCreateResponse.of(savedDiary);
    }

    public DiaryModifyResponse modifyDiary(final String providerId, final long diaryId, final LocalDate currentDate, DiaryModifyServiceRequest request) {
        return null;
    }

    public DiaryModifyResponse modifyDiaryImage(final String memberKey, final Long diaryId, final MultipartFile file) {
        return null;
    }

    public DiaryRemoveResponse removeDiary(final String memberKey, final Long diaryId) {
        return null;
    }
}
