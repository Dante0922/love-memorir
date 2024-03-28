package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.diary.Diary;
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
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.lovememoir.server.api.service.diary.DiaryValidator.validateRelationshipStartedDate;
import static com.lovememoir.server.api.service.diary.DiaryValidator.validateTitle;
import static com.lovememoir.server.common.constant.GlobalConstant.MAX_DIARY_COUNT;
import static com.lovememoir.server.common.message.ExceptionMessage.*;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    public DiaryCreateResponse createDiary(final String memberKey, final LocalDateTime currentDateTime, DiaryCreateServiceRequest request) {
        final String title = validateTitle(request.getTitle());
        final LocalDate relationshipStartedDate = validateRelationshipStartedDate(request.isInLove(), request.getRelationshipStartedDate(), currentDateTime);

        final Member member = getMember(memberKey);

        validateMaximumDiaryCount(member.getId());

        final Diary savedDiary = saveDiary(title, request.isInLove(), relationshipStartedDate, member);

        return DiaryCreateResponse.of(savedDiary);
    }

    public DiaryModifyResponse modifyDiary(final String memberKey, final Long diaryId, final LocalDateTime currentDateTime, DiaryModifyServiceRequest request) {
        final String title = validateTitle(request.getTitle());
        final LocalDate relationshipStartedDate = validateRelationshipStartedDate(request.isInLove(), request.getRelationshipStartedDate(), currentDateTime);

        final Diary diary = getMyDiary(memberKey, diaryId);

        diary.modify(generateTitle(title), request.isInLove(), relationshipStartedDate);

        return DiaryModifyResponse.of(diary);
    }

    public DiaryModifyResponse modifyDiaryImage(final String memberKey, final Long diaryId, final MultipartFile file) {
        Diary diary = getMyDiary(memberKey, diaryId);

        UploadFile uploadFile = fileUploadToAwsS3(file);

        diary.modifyFile(uploadFile);

        return DiaryModifyResponse.of(diary);
    }

    public DiaryRemoveResponse removeDiary(final String memberKey, final Long diaryId) {
        final Diary diary = getMyDiary(memberKey, diaryId);

        diary.remove();

        return DiaryRemoveResponse.of(diary);
    }

    private Member getMember(final String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
    }

    private void validateMaximumDiaryCount(final Long memberId) {
        final int diaryCount = diaryRepository.countByMemberId(memberId);
        if (diaryCount >= MAX_DIARY_COUNT) {
            throw new IllegalArgumentException(MAXIMUM_DIARY_COUNT);
        }
    }

    private String generateTitle(final String title) {
        return title + "와의 연애 기록";
    }

    private Diary saveDiary(String title, boolean inLove, LocalDate relationshipStartedDate, Member member) {
        final Diary diary = Diary.create(generateTitle(title), inLove, relationshipStartedDate, member);
        return diaryRepository.save(diary);
    }

    private Diary getDiary(Long diaryId) {
        return diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));
    }

    private Diary getMyDiary(String memberKey, Long diaryId) {
        final Member member = getMember(memberKey);
        final Diary diary = getDiary(diaryId);

        if (!diary.isMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        return diary;
    }

    private UploadFile fileUploadToAwsS3(MultipartFile file) {
        try {
            return fileStore.storeFile(file);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAIL_UPLOAD_FILE, e);
        }
    }
}
