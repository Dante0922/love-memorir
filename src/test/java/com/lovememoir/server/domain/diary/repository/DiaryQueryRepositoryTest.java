package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Role;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class DiaryQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DiaryQueryRepository diaryQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @DisplayName("회원 고유키로 일기장 목록을 조회한다.")
    @Test
    void findByMemberKey() {
        //given
        Member member = createMember();
        Diary diary1 = createDiary(member, false, "루이바오와의 연애 기록", false);
        Diary diary2 = createDiary(member, false, "후이바오와의 연애 기록", false);
        Diary diary3 = createDiary(member, false, "강바오와의 연애 기록", true);
        Diary diary4 = createDiary(member, true, "러바오와의 연애 기록", false);

        //when
        List<DiarySearchResponse> responses = diaryQueryRepository.findByMemberKey(member.getMemberKey(), false);

        //then
        assertThat(responses).hasSize(3)
            .extracting("isMain", "title", "pageCount")
            .containsExactly(
                tuple(true, "러바오와의 연애 기록", 0),
                tuple(false, "후이바오와의 연애 기록", 0),
                tuple(false, "루이바오와의 연애 기록", 0)
            );
    }

    @DisplayName("회원 고유키로 메인 일기장 목록을 조회한다.")
    @Test
    void findMainDiaries() {
        //given
        Member member = createMember();
        Diary diary1 = createDiary(member, false, "루이바오와의 연애 기록", false);
        Diary diary2 = createDiary(member, false, "후이바오와의 연애 기록", false);
        Diary diary3 = createDiary(member, true, "강바오와의 연애 기록", true);
        Diary diary4 = createDiary(member, true, "러바오와의 연애 기록", false);

        //when
        List<DiarySearchResponse> responses = diaryQueryRepository.findByMemberKey(member.getMemberKey(), true);

        //then
        assertThat(responses).hasSize(1)
            .extracting("isMain", "title", "pageCount")
            .containsExactly(
                tuple(true, "러바오와의 연애 기록", 0)
            );
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

    private Diary createDiary(Member member, boolean isFixed, String title, boolean isDeleted) {
        Diary diary = Diary.builder()
            .isFixed(isFixed)
            .title(title)
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .pageCount(0)
            .member(member)
            .isDeleted(isDeleted)
            .build();
        return diaryRepository.save(diary);
    }
}