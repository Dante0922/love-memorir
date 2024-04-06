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
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
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

}