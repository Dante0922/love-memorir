package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
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
import java.util.Optional;

import static com.lovememoir.server.api.service.diarypage.DiaryPageValidator.*;
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

    private Member getMember(final String memberKey) {
        Optional<Member> findMember = memberRepository.findByMemberKey(memberKey);
        if (findMember.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_MEMBER);
        }
        return findMember.get();
    }

    private Diary getDiary(final Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if (findDiary.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_DIARY);
        }
        return findDiary.get();
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
}
