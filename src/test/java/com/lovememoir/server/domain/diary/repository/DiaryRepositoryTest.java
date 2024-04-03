package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DiaryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원이 등록한 일기장 갯수를 조회한다.")
    @Test
    void countByMemberId() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member, false);
        Diary removedDiary = createDiary(member, true);

        //when
        int diaryCount = diaryRepository.countByMemberId(member.getId());

        //then
        assertThat(diaryCount).isOne();
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

    private Diary createDiary(Member member, boolean isDeleted) {
        Diary diary = Diary.builder()
            .isFixed(false)
            .title("러바오와의 연애 기록")
            .isInLove(true)
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .pageCount(0)
            .member(member)
            .isDeleted(isDeleted)
            .build();
        return diaryRepository.save(diary);
    }
}