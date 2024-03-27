package com.lovememoir.server.api.service.diaryanalysis;

import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisRepository;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryAnalysisService {

    private final DiaryAnalysisRepository diaryAnalysisRepository;
    private final DiaryPageRepository diaryPageRepository;
    private final OpenAiChatClient chatClient;

    public void diaryAnalysis(final Long diaryPageId) {

    }
}
