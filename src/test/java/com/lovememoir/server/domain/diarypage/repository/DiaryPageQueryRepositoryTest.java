package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageResponse;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Role;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DiaryPageQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DiaryPageQueryRepository diaryPageQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @DisplayName("일기장 식별키로 일기 식별키 목록을 조회한다.")
    @Test
    void findAllIdByDiaryId() {
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
        List<Long> diaryIds = diaryPageQueryRepository.findAllIdByDiaryId(diary.getId(), pageRequest);

        //then
        assertThat(diaryIds).hasSize(2)
            .containsExactly(diaryPage3.getId(), diaryPage1.getId());
    }

    @DisplayName("일기 식별키 목록으로 일기 정보를 조회한다.")
    @Test
    void findAllByDiaryIdIn() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage1 = createDiaryPage(diary, false);
        DiaryPage diaryPage2 = createDiaryPage(diary, false);

        List<Long> diaryIds = List.of(diaryPage2.getId(), diaryPage1.getId());

        //when
        List<DiaryPagesResponse> content = diaryPageQueryRepository.findAllByDiaryIdIn(diaryIds);

        //then
        assertThat(content).hasSize(2)
            .extracting("diaryPageId")
            .containsExactly(diaryPage2.getId(), diaryPage1.getId());
    }

    @DisplayName("일기 페이지 식별키로 일기를 조회한다.")
    @Test
    void findById() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage = createDiaryPage(diary, false);

        //when
        Optional<DiaryPageResponse> response = diaryPageQueryRepository.findById(diaryPage.getId());

        //then
        assertThat(response).isPresent();
        assertThat(response.get())
            .hasFieldOrPropertyWithValue("diaryPageId", diaryPage.getId())
            .hasFieldOrPropertyWithValue("pageTitle", diaryPage.getTitle())
            .hasFieldOrPropertyWithValue("pageContent", diaryPage.getContent())
            .hasFieldOrPropertyWithValue("diaryDate", diaryPage.getDiaryDate())
            .hasFieldOrPropertyWithValue("dateTimeOfCreation", diaryPage.getCreatedDateTime());
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