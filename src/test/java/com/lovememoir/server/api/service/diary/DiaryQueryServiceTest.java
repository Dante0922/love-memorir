package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class DiaryQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryQueryService diaryQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @DisplayName("회원 정보를 입력 받아 등록된 일기장 목록을 조회한다.")
    @Test
    void searchDiaries() {
        //given
        Member member = createMember();
        Auth auth = createAuth(member);
        Diary diary1 = createDiary(member, false, false, false, "푸바오");
        Diary diary2 = createDiary(member, true, false, false, "강바오");
        Diary diary3 = createDiary(member, false, true, false, "아이바오");
        Diary diary4 = createDiary(member, false, false, false, "루이바오");
        Diary diary5 = createDiary(member, false, false, true, "러바오");
        Diary diary6 = createDiary(member, false, false, false, "후이바오");

        //when
        List<DiarySearchResponse> content = diaryQueryService.searchDiaries(auth.getProviderId());

        //then
        assertThat(content).hasSize(4)
            .extracting("diaryId", "title", "isMain")
            .containsExactly(
                tuple(diary3.getId(), "아이바오", true),
                tuple(diary6.getId(), "후이바오", false),
                tuple(diary4.getId(), "루이바오", false),
                tuple(diary1.getId(), "푸바오", false)
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

    private Auth createAuth(Member member) {
        Auth auth = Auth.builder()
            .provider(ProviderType.KAKAO)
            .providerId("0123456789")
            .accessToken("access.token")
            .refreshToken("refresh.token")
            .expiredAt(null)
            .member(member)
            .build();
        return authRepository.save(auth);
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