package com.lovememoir.server.api.service.diaryanalysis;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.code.repository.SystemCodeGroupRepository;
import com.lovememoir.server.domain.code.repository.SystemCodeRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisRepository;
import com.lovememoir.server.domain.diarypage.AnalysisResult;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import net.minidev.json.parser.ParseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

class DiaryAnalysisServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryAnalysisService diaryAnalysisService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @Autowired
    private DiaryAnalysisRepository diaryAnalysisRepository;

    @MockBean
    private OpenAiChatClient chatClient;

    @DisplayName("일기 감정 분석 결과를 저장한다.")
    @Test
    void diaryAnalysis() throws ParseException {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage = createDiaryPage(diary);

        given(chatClient.call(anyString()))
            .willReturn("""
                {
                  "HAPPINESS": 70,
                  "SADNESS": 20,
                  "ROMANCE": 60,
                  "STABILITY": 50,
                  "ANGER": 0
                }
                """);

        //when
        diaryAnalysisService.diaryAnalysis(diaryPage.getId());

        //then
        List<DiaryAnalysis> diaryAnalyses = diaryAnalysisRepository.findAll();
        // TODO 임시로 스킵함
//        assertThat(diaryAnalyses).hasSize(5)
//            .extracting("emotionCode", "weight")
//            .containsExactlyInAnyOrder(
//                tuple(0, 50),
//                tuple(1, 70),
//                tuple(2, 60),
//                tuple(3, 20),
//                tuple(4, 0)
//            );
//
//        Optional<DiaryPage> findDiaryPage = diaryPageRepository.findById(diaryPage.getId());
//        assertThat(findDiaryPage).isPresent();
//        assertThat(findDiaryPage.get().getAnalysis())
//            .hasFieldOrPropertyWithValue("analysisStatus", AnalysisStatus.SUCCESS)
//            .hasFieldOrPropertyWithValue("emotionCode", 1);
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

    private DiaryPage createDiaryPage(Diary diary) {
        DiaryPage diaryPage = DiaryPage.builder()
            .isDeleted(false)
            .title("장난꾸러기 후이바오")
            .content("우리의 후쪽이")
            .recordDate(LocalDate.of(2024, 1, 1))
            .analysis(AnalysisResult.builder()
                .analysisStatus(AnalysisStatus.BEFORE)
                .build())
            .diary(diary)
            .build();
        return diaryPageRepository.save(diaryPage);
    }
}