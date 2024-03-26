package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.lovememoir.server.domain.member.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.Role;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class DiaryPageQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DiaryPageQueryRepository diaryPageQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @DisplayName("일기장 식별키로 일기 목록을 조회한다.")
    @Test
    void findByDiaryId() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage1 = createDiaryPage(diary, false);
        DiaryPage diaryPage2 = createDiaryPage(diary, true);
        DiaryPage diaryPage3 = createDiaryPage(diary, false);

        DiaryPage diaryPage4 = createDiaryPage(diary, false);
        DiaryPage diaryPage5 = createDiaryPage(diary, true);
        DiaryPage diaryPage6 = createDiaryPage(diary, false);

        PageRequest pageRequest = PageRequest.of(1, 2);

        //when
        Slice<DiaryPagesResponse> content = diaryPageQueryRepository.findByDiaryId(diary.getId(), pageRequest);

        //then
        assertThat(content.getContent()).hasSize(2)
            .extracting("diaryPageId")
            .containsExactly(
                tuple(diaryPage3.getId()),
                tuple(diaryPage1.getId())
            );
        assertThat(content.getNumber()).isEqualTo(1);
        assertThat(content.getSize()).isEqualTo(2);
        assertThat(content.isFirst()).isFalse();
        assertThat(content.isLast()).isTrue();
    }

    private Member createMember() {
        Member member = Member.builder()
            .memberKey(UUID.randomUUID().toString())
            .nickname("아이바오")
            .gender(Gender.F)
            .birth("2013-07-13")
            .role(Role.USER)
            .build();
        return memberRepository.save(member);
    }

    private Diary createDiary(Member member) {
        Diary diary = Diary.builder()
            .isFixed(false)
            .title("러바오와의 연애 기록")
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .pageCount(0)
            .member(member)
            .build();
        return diaryRepository.save(diary);
    }

    private DiaryPage createDiaryPage(Diary diary, boolean isDeleted) {
        DiaryPage diaryPage = DiaryPage.builder()
            .title("title")
            .content("content")
            .diaryDate(LocalDate.of(2024, 3, 1))
            .diary(diary)
            .isDeleted(isDeleted)
            .build();
        return diaryPageRepository.save(diaryPage);
    }
}