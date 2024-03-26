package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.member.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.Role;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_AUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiaryPageServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryPageService diaryPageService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @DisplayName("신규 일기 등록시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void createDiaryPageWithoutAuth() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);

        Member otherMember = createMember();

        DiaryPageCreateServiceRequest request = DiaryPageCreateServiceRequest.builder()
            .title("러바오 없이 쌍둥이 육아")
            .content("혼자 루이바오랑 후이바오를 육아하기 너무 힘들다...너무 개구쟁이들이야")
            .diaryDate(LocalDate.of(2024, 3, 20))
            .build();

        //when //then
        assertThatThrownBy(() -> diaryPageService.createDiaryPage(otherMember.getMemberKey(), diary.getId(), request))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);

        List<DiaryPage> diaryPages = diaryPageRepository.findAll();
        assertThat(diaryPages).isEmpty();

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get().getPageCount()).isZero();
    }

    @DisplayName("회원 고유키, 일기장 식별키, 일기 정보를 입력 받아 신규 일기를 등록한다. 등록에 성공하면 일기 페이지 수가 1 증가한다.")
    @Test
    void createDiaryPage() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);

        DiaryPageCreateServiceRequest request = DiaryPageCreateServiceRequest.builder()
            .title("러바오 없이 쌍둥이 육아")
            .content("혼자 루이바오랑 후이바오를 육아하기 너무 힘들다...너무 개구쟁이들이야")
            .diaryDate(LocalDate.of(2024, 3, 20))
            .build();

        //when
        DiaryPageCreateResponse response = diaryPageService.createDiaryPage(member.getMemberKey(), diary.getId(), request);

        //then
        assertThat(response).isNotNull();

        Optional<DiaryPage> findDiaryPage = diaryPageRepository.findById(response.getDiaryPageId());
        assertThat(findDiaryPage).isPresent();
        assertThat(findDiaryPage.get())
            .hasFieldOrPropertyWithValue("title", "러바오 없이 쌍둥이 육아")
            .hasFieldOrPropertyWithValue("content", "혼자 루이바오랑 후이바오를 육아하기 너무 힘들다...너무 개구쟁이들이야")
            .hasFieldOrPropertyWithValue("diaryDate", LocalDate.of(2024, 3, 20));

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get().getPageCount()).isOne();
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
}