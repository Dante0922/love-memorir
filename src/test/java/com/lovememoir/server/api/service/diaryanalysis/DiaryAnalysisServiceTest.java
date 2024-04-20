package com.lovememoir.server.api.service.diaryanalysis;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.code.repository.SystemCodeGroupRepository;
import com.lovememoir.server.domain.code.repository.SystemCodeRepository;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisRepository;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

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