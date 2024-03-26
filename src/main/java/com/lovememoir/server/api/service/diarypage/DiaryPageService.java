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
        String title = validateTitle(request.getTitle());
        LocalDate diaryDate = validateDiaryDate(currentDateTime, request.getDiaryDate());

        Diary diary = getMyDiary(memberKey, diaryId);

        DiaryPage savedDiaryPage = saveDiaryPage(request, title, diaryDate, diary);

        return DiaryPageCreateResponse.of(savedDiaryPage);
    }

    public DiaryPageModifyResponse modifyDiaryPage(final String memberKey, final Long diaryPageId, final LocalDateTime currentDateTime, DiaryPageModifyServiceRequest request) {
        String title = validateTitle(request.getTitle());
        LocalDate diaryDate = validateDiaryDate(currentDateTime, request.getDiaryDate());

        DiaryPage diaryPage = getMyDiaryPage(memberKey, diaryPageId);

        diaryPage.modify(title, request.getContent(), diaryDate);

        return DiaryPageModifyResponse.of(diaryPage);
    }

    public DiaryPageRemoveResponse removeDiaryPage(final String memberKey, final Long diaryPageId) {
        DiaryPage diaryPage = getMyDiaryPage(memberKey, diaryPageId);

        diaryPage.remove();

        return DiaryPageRemoveResponse.of(diaryPage);
    }

    private Member getMember(final String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
    }

    private Diary getDiary(final Long diaryId) {
        return diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));
    }

    private Diary getMyDiary(final String memberKey, final Long diaryId) {
        final Member member = getMember(memberKey);
        final Diary diary = getDiary(diaryId);

        if (!diary.isMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        return diary;
    }

    private DiaryPage saveDiaryPage(DiaryPageCreateServiceRequest request, String title, LocalDate diaryDate, Diary diary) {
        DiaryPage diaryPage = DiaryPage.create(title, request.getContent(), diaryDate, diary);
        return diaryPageRepository.save(diaryPage);
    }

    private DiaryPage getDiaryPage(final Long diaryPageId) {
        return diaryPageRepository.findByIdWithDiary(diaryPageId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY_PAGE));
    }

    private DiaryPage getMyDiaryPage(final String memberKey, final Long diaryPageId) {
        final Member member = getMember(memberKey);
        final DiaryPage diaryPage = getDiaryPage(diaryPageId);

        if (!diaryPage.getDiary().isMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        return diaryPage;
    }
}
