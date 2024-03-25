package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.Role;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.lovememoir.server.common.message.ExceptionMessage.MAXIMUM_DIARY_COUNT;
import static com.lovememoir.server.common.message.ExceptionMessage.NO_AUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiaryServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @DisplayName("신규 일기장 등록 시 생성 가능한 일기장 최대 갯수를 초과하면 예외가 발생한다.")
    @Test
    void createDiaryWithMaxDiary() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary1 = createDiary(member);
        Diary diary2 = createDiary(member);
        Diary diary3 = createDiary(member);

        DiaryCreateServiceRequest request = DiaryCreateServiceRequest.builder()
            .title("러바오")
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .build();

        //when //then
        assertThatThrownBy(() -> diaryService.createDiary(member.getMemberKey(), currentDateTime, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAXIMUM_DIARY_COUNT);

        List<Diary> diaries = diaryRepository.findAll();
        assertThat(diaries).hasSize(3);
    }

    @DisplayName("회원 고유키와 일기장 정보를 입력 받아 신규 일기장을 등록한다.")
    @Test
    void createDiary() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary1 = createDiary(member);
        Diary diary2 = createDiary(member);

        DiaryCreateServiceRequest request = DiaryCreateServiceRequest.builder()
            .title("러바오")
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .build();

        //when
        DiaryCreateResponse response = diaryService.createDiary(member.getMemberKey(), currentDateTime, request);

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(response.getDiaryId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get())
            .hasFieldOrPropertyWithValue("isFixed", false)
            .hasFieldOrPropertyWithValue("title", "러바오와의 연애 기록")
            .hasFieldOrPropertyWithValue("relationshipStartedDate", LocalDate.of(2016, 3, 3))
            .hasFieldOrPropertyWithValue("pageCount", 0);
    }

    @DisplayName("일기장 수정시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void modifyDiaryWithoutAuth() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary = createDiary(member);

        Member otherMember = createMember();

        DiaryModifyServiceRequest request = DiaryModifyServiceRequest.builder()
            .title("루이바오")
            .relationshipStartedDate(LocalDate.of(2023, 7, 7))
            .build();

        //when //then
        assertThatThrownBy(() -> diaryService.modifyDiary(otherMember.getMemberKey(), diary.getId(), currentDateTime, request))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);
    }

    @DisplayName("회원 고유키와 일기장 정보를 입력 받아 일기장을 수정한다.")
    @Test
    void modifyDiary() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary = createDiary(member);

        DiaryModifyServiceRequest request = DiaryModifyServiceRequest.builder()
            .title("루이바오")
            .relationshipStartedDate(LocalDate.of(2023, 7, 7))
            .build();

        //when
        DiaryModifyResponse response = diaryService.modifyDiary(member.getMemberKey(), diary.getId(), currentDateTime, request);

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get())
            .hasFieldOrPropertyWithValue("isFixed", false)
            .hasFieldOrPropertyWithValue("title", "루이바오와의 연애 기록")
            .hasFieldOrPropertyWithValue("relationshipStartedDate", LocalDate.of(2023, 7, 7))
            .hasFieldOrPropertyWithValue("pageCount", 0);
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