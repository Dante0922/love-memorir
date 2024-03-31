package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.AnalysisResult;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageResponse;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DiaryPageQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryPageQueryService diaryPageQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @DisplayName("일기장 식별키와 페이징 정보를 입력 받아 일기 목록을 조회한다.")
    @Test
    void searchDiaryPages() {
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
        SliceResponse<DiaryPagesResponse> response = diaryPageQueryService.searchDiaryPages(diary.getId(), pageRequest);

        //then
        assertThat(response)
            .isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 2)
            .hasFieldOrPropertyWithValue("size", 2)
            .hasFieldOrPropertyWithValue("isFirst", false)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(2)
            .extracting("diaryPageId")
            .containsExactly(diaryPage3.getId(), diaryPage1.getId());
    }

    @DisplayName("일기 페이지 식별키를 입력 받아 일기 정보를 조회한다.")
    @Test
    void searchDiaryPage() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage = createDiaryPage(diary, false);

        //when
        DiaryPageResponse response = diaryPageQueryService.searchDiaryPage(diaryPage.getId());

        //then
        assertThat(response)
            .isNotNull()
            .hasFieldOrPropertyWithValue("diaryPageId", diaryPage.getId())
            .hasFieldOrPropertyWithValue("pageTitle", diaryPage.getTitle())
            .hasFieldOrPropertyWithValue("pageContent", diaryPage.getContent())
            .hasFieldOrPropertyWithValue("diaryDate", diaryPage.getDiaryDate())
            .hasFieldOrPropertyWithValue("createdDateTime", diaryPage.getCreatedDateTime());
    }

    private Member createMember() {
        Member member = Member.builder()
            .memberKey(UUID.randomUUID().toString())
            .nickname("아이바오")
            .gender(Gender.F)
            .birth("2013-07-13")
            .roleType(RoleType.USER)
            .build();
        return memberRepository.save(member);
    }

    private Diary createDiary(Member member) {
        Diary diary = Diary.builder()
            .isFixed(false)
            .title("러바오와의 연애 기록")
            .isInLove(true)
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
            .analysisResult(AnalysisResult.builder()
                .analysisStatus(AnalysisStatus.BEFORE)
                .build())
            .diary(diary)
            .isDeleted(isDeleted)
            .build();
        return diaryPageRepository.save(diaryPage);
    }
}