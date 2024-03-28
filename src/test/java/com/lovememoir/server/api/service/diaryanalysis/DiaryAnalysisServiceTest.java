package com.lovememoir.server.api.service.diaryanalysis;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.code.SystemCode;
import com.lovememoir.server.domain.code.SystemCodeGroup;
import com.lovememoir.server.domain.code.repository.SystemCodeGroupRepository;
import com.lovememoir.server.domain.code.repository.SystemCodeRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisRepository;
import com.lovememoir.server.domain.diarypage.AnalysisResult;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.member.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.Role;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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

    @Autowired
    private SystemCodeGroupRepository codeGroupRepository;

    @Autowired
    private SystemCodeRepository codeRepository;

    @MockBean
    private OpenAiChatClient chatClient;

    @DisplayName("일기 페이지 식별키를 입력 받아 일기 내용을 분석한 결과를 저장한다.")
    @Test
    void diaryAnalysis() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage = createDiaryPage(diary);
        SystemCodeGroup group = createCodeGroup();
        SystemCode code1 = createEmotionCode(group, "행복");
        SystemCode code2 = createEmotionCode(group, "설렘");
        SystemCode code3 = createEmotionCode(group, "안정");
        SystemCode code4 = createEmotionCode(group, "슬픔");
        SystemCode code5 = createEmotionCode(group, "분노");

        given(chatClient.call(anyString()))
            .willReturn("행복: 8\n설렘: 7\n안정: 6\n슬픔: 4\n분노: 1");

        //when
        diaryAnalysisService.diaryAnalysis(diaryPage.getId(), group.getCode());

        //then
        List<DiaryAnalysis> findDiaryAnalyses = diaryAnalysisRepository.findAll();
        assertThat(findDiaryAnalyses).hasSize(5);

        Optional<DiaryPage> findDiaryPage = diaryPageRepository.findById(diaryPage.getId());
        assertThat(findDiaryPage).isPresent();
        assertThat(findDiaryPage.get())
            .hasFieldOrPropertyWithValue("analysisResult.analysisStatus", AnalysisStatus.SUCCESS)
            .hasFieldOrPropertyWithValue("analysisResult.emotionCode", code1.getCode());
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
            .isInLove(true)
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .pageCount(0)
            .member(member)
            .build();
        return diaryRepository.save(diary);
    }

    private DiaryPage createDiaryPage(Diary diary) {
        DiaryPage diaryPage = DiaryPage.builder()
            .title("개구쟁이 쌍둥바오")
            .content("혼자 루이바오랑 후이바오를 육아하기 너무 힘들다...너무 개구쟁이들이야")
            .diaryDate(LocalDate.of(2024, 3, 10))
            .analysisResult(AnalysisResult.builder()
                .analysisStatus(AnalysisStatus.BEFORE)
                .build())
            .diary(diary)
            .build();
        return diaryPageRepository.save(diaryPage);
    }

    private SystemCodeGroup createCodeGroup() {
        SystemCodeGroup group = SystemCodeGroup.builder()
            .name("감정")
            .build();
        return codeGroupRepository.save(group);
    }

    private SystemCode createEmotionCode(SystemCodeGroup group, String emotion) {
        SystemCode code = SystemCode.builder()
            .name(emotion)
            .group(group)
            .build();
        return codeRepository.save(code);
    }
}