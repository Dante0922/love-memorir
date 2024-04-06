package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class DiaryQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DiaryQueryRepository diaryQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @DisplayName("회원 식별키로 일기장 목록을 조회한다.")
    @Test
    void findAllByMemberId() {
        //given
        Member member = createMember();
        Diary diary1 = createDiary(member, false, false, false, "푸바오");
        Diary diary2 = createDiary(member, true, false, false, "강바오");
        Diary diary3 = createDiary(member, false, true, false, "아이바오");
        Diary diary4 = createDiary(member, false, false, false, "루이바오");
        Diary diary5 = createDiary(member, false, false, true, "러바오");
        Diary diary6 = createDiary(member, false, false, false, "후이바오");

        //when
        List<DiarySearchResponse> content = diaryQueryRepository.findAllByMemberId(member.getId());

        //then
        assertThat(content).hasSize(3)
            .extracting("diaryId", "title")
            .containsExactly(
                tuple(diary6.getId(), "후이바오"),
                tuple(diary4.getId(), "루이바오"),
                tuple(diary1.getId(), "푸바오")
            );
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

    private Diary createDiary(Member member, boolean isDeleted, boolean isMain, boolean isStored, String title) {
        Diary diary = Diary.builder()
            .isDeleted(isDeleted)
            .isMain(isMain)
            .title(title)
            .loveInfo(LoveInfo.builder()
                .isLove(false)
                .build())
            .pageCount(0)
            .profile(null)
            .isStored(isStored)
            .member(member)
            .build();
        return diaryRepository.save(diary);
    }
}