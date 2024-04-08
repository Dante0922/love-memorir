package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.AnalysisResult;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @DisplayName("일기장에 등록된 일기의 아이디를 조회한다.")
    @Test
    void findAllIdByDiaryId() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage1 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 20));
        DiaryPage diaryPage2 = createDiaryPage(diary, true, LocalDate.of(2024, 3, 20));
        DiaryPage diaryPage3 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 22));
        DiaryPage diaryPage4 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 21));
        DiaryPage diaryPage5 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 21));
        DiaryPage diaryPage6 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 20));

        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        List<Long> ids = diaryPageQueryRepository.findAllIdByDiaryId(diary.getId(), pageRequest);

        //then
        assertThat(ids).hasSize(4)
            .containsExactly(diaryPage3.getId(), diaryPage5.getId(), diaryPage4.getId(), diaryPage6.getId());
    }

    @DisplayName("일기 식별키 목록으로 일기를 조회한다.")
    @Test
    void findAllByDiaryIdIn() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage3 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 22));
        DiaryPage diaryPage4 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 21));
        DiaryPage diaryPage5 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 21));
        List<Long> diaryPageIds = List.of(diaryPage3.getId(), diaryPage5.getId(), diaryPage4.getId());

        //when
        List<DiaryPagesResponse> content = diaryPageQueryRepository.findAllByDiaryIdIn(diaryPageIds);

        //then
        assertThat(content).hasSize(3)
            .extracting("diaryPageId")
            .containsExactly(diaryPage3.getId(), diaryPage5.getId(), diaryPage4.getId());
    }

    @DisplayName("일기 식별키로 일기를 조회한다.")
    @Test
    void findById() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage = createDiaryPage(diary, false, LocalDate.of(2024, 3, 22));

        //when
        Optional<DiaryPage> findDiaryPage = diaryPageRepository.findById(diary.getId());

        //then
        assertThat(findDiaryPage).isPresent();
        assertThat(findDiaryPage.get())
            .hasFieldOrPropertyWithValue("diaryPageId", diaryPage.getId())
            .hasFieldOrPropertyWithValue("analysisStatus", AnalysisStatus.BEFORE)
            .hasFieldOrPropertyWithValue("emotionCode", null)
            .hasFieldOrPropertyWithValue("title", "장난꾸러기 후이바오")
            .hasFieldOrPropertyWithValue("content", "우리의 후쪽이")
            .hasFieldOrPropertyWithValue("recordDate", LocalDate.of(2024, 3, 22))
            .hasFieldOrPropertyWithValue("createdDateTime", diaryPage.getCreatedDateTime());
    }

    private Member createMember() {
        Member member = Member.builder()
            .nickname("아이바오")
            .gender(Gender.F)
            .birth("2013-07-13")
            .roleType(RoleType.USER)
            .build();
        return memberRepository.save(member);
    }

    private Diary createDiary(Member member) {
        Diary diary = Diary.builder()
            .isDeleted(false)
            .isMain(true)
            .title("후이바오")
            .loveInfo(LoveInfo.builder()
                .isLove(false)
                .startedDate(null)
                .finishedDate(null)
                .build())
            .pageCount(0)
            .profile(null)
            .isStored(false)
            .member(member)
            .build();
        return diaryRepository.save(diary);
    }

    private DiaryPage createDiaryPage(Diary diary, boolean isDeleted, LocalDate recordDate) {
        DiaryPage diaryPage = DiaryPage.builder()
            .isDeleted(isDeleted)
            .title("장난꾸러기 후이바오")
            .content("우리의 후쪽이")
            .recordDate(recordDate)
            .analysis(AnalysisResult.builder()
                .analysisStatus(AnalysisStatus.BEFORE)
                .build())
            .diary(diary)
            .build();
        return diaryPageRepository.save(diaryPage);
    }
}