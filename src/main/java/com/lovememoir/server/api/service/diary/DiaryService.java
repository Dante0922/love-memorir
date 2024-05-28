package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.lovememoir.server.api.service.diary.DiaryValidator.validateLoveInfo;
import static com.lovememoir.server.api.service.diary.DiaryValidator.validateTitle;
import static com.lovememoir.server.common.message.ExceptionMessage.*;

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

        Optional<Long> mainDiary = diaryRepository.findMainDiaryByMemberId(member.getId());

        boolean isMain = mainDiary.isEmpty();

        Diary diary = Diary.create(title, loveInfo, member, isMain);
        Diary savedDiary = diaryRepository.save(diary);

        return DiaryCreateResponse.of(savedDiary);
    }

    public DiaryModifyResponse modifyDiary(final String providerId, final long diaryId, final LocalDate currentDate, DiaryModifyServiceRequest request) {
        String title = validateTitle(request.getTitle());
        LoveInfo loveInfo = validateLoveInfo(request.isLove(), request.getStartedDate(), request.getFinishedDate(), currentDate);

        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));

        if (diary.isNotMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        diary.modify(title, loveInfo);

        return DiaryModifyResponse.of(diary);
    }

    public DiaryModifyResponse modifyDiaryProfile(final String providerId, final long diaryId, final MultipartFile file) {
        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));

        if (diary.isNotMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        UploadFile uploadFile = cloudUploadFile(file);
        diary.modifyProfile(uploadFile);

        return DiaryModifyResponse.of(diary);
    }

    public DiaryModifyResponse modifyDiaryStoreStatus(final String providerId, final long diaryId) {
        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));

        if (diary.isNotMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        diary.modifyStoreStatus();

        return DiaryModifyResponse.of(diary);
    }

    public DiaryModifyResponse modifyDiaryMainStatus(final String providerId, final long diaryId, final boolean isMain) {
        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));

        if (diary.isNotMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        diary.modifyMainStatus(isMain);

        return DiaryModifyResponse.of(diary);
    }

    public DiaryRemoveResponse removeDiary(final String providerId, final long diaryId) {
        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));

        if (diary.isNotMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        diary.remove();

        return DiaryRemoveResponse.of(diary);
    }

    private UploadFile cloudUploadFile(MultipartFile file) {
        try {
            return fileStore.storeFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
