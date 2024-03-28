package com.lovememoir.server.api.service.diaryanalysis;

import com.lovememoir.server.domain.code.SystemCode;
import com.lovememoir.server.domain.code.repository.SystemCodeRepository;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_DIARY_PAGE;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryAnalysisService {

    private final DiaryAnalysisRepository diaryAnalysisRepository;
    private final DiaryPageRepository diaryPageRepository;
    private final SystemCodeRepository codeRepository;
    private final OpenAiChatClient chatClient;

    public void diaryAnalysis(final Long diaryPageId, final int groupCode) {
        List<SystemCode> emotionCodes = codeRepository.findAllByGroupCode(groupCode);
        Map<String, Integer> emotionCodeMap = new HashMap<>();
        for (SystemCode emotionCode : emotionCodes) {
            emotionCodeMap.put(emotionCode.getName(), emotionCode.getCode());
        }

        DiaryPage diaryPage = diaryPageRepository.findById(diaryPageId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY_PAGE));

        String prompt = OpenaiPrompt.generatePrompt(diaryPage.getContent());

        String response = chatClient.call(prompt);

        String[] results = response.split("\n");
        List<DiaryAnalysis> diaryAnalyses = new ArrayList<>();
        for (String result : results) {
            String[] emotion = result.split(": ");
            int emotionCode = emotionCodeMap.get(emotion[0]);
            int weight = Integer.parseInt(emotion[1]);
            diaryAnalyses.add(DiaryAnalysis.create(emotionCode, weight, diaryPage));
        }

        List<DiaryAnalysis> savedDiaryAnalyses = diaryAnalysisRepository.saveAll(diaryAnalyses);

        savedDiaryAnalyses.sort((a, b) -> b.getWeight() - a.getWeight());

        DiaryAnalysis maxWeightEmotion = savedDiaryAnalyses.get(0);

        diaryPage.successAnalysis(maxWeightEmotion.getEmotionCode());
    }
}
