package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.lovememoir.server.api.service.diary.DiaryValidator.validateRelationshipStartedDate;
import static com.lovememoir.server.api.service.diary.DiaryValidator.validateTitle;
import static com.lovememoir.server.common.constant.GlobalConstant.MAX_DIARY_COUNT;
import static com.lovememoir.server.common.message.ExceptionMessage.MAXIMUM_DIARY_COUNT;
import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_MEMBER;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    public DiaryCreateResponse createDiary(final String memberKey, final LocalDateTime currentDateTime, DiaryCreateServiceRequest request) {
        final String title = validateTitle(request.getTitle());
        final LocalDate relationshipStartedDate = validateRelationshipStartedDate(currentDateTime, request.getRelationshipStartedDate());

        final Optional<Member> findMember = memberRepository.findByMemberKey(memberKey);
        if (findMember.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_MEMBER);
        }
        final Member member = findMember.get();

        final int diaryCount = diaryRepository.countByMemberId(member.getId());
        if (diaryCount >= MAX_DIARY_COUNT) {
            throw new IllegalArgumentException(MAXIMUM_DIARY_COUNT);
        }

        final Diary diary = Diary.create(generateTitle(title), relationshipStartedDate, member);
        final Diary savedDiary = diaryRepository.save(diary);

        return DiaryCreateResponse.of(savedDiary);
    }

    private String generateTitle(final String title) {
        return title + "와의 연애 기록";
    }
}
