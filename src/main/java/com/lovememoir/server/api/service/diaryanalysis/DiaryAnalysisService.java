package com.lovememoir.server.api.service.diaryanalysis;

import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.code.repository.SystemCodeRepository;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
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
    private final OpenAiChatClient chatClient;

    public void diaryAnalysis(final Long diaryPageId) throws ParseException {
        DiaryPage diaryPage = diaryPageRepository.findById(diaryPageId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY_PAGE));

        String prompt = OpenaiPrompt.generatePrompt(diaryPage.getContent());

        String response = chatClient.call(prompt);

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(response);
        JSONObject json = (JSONObject) obj;

        List<DiaryAnalysis> diaryAnalyses = new ArrayList<>();
        for (Emotion emotion : Emotion.values()) {
            int emotionCode = emotion.getCode();
            int weight = (int) json.get(emotion.getText());
            diaryAnalyses.add(DiaryAnalysis.create(emotionCode, weight, diaryPage));
        }

        List<DiaryAnalysis> savedDiaryAnalyses = diaryAnalysisRepository.saveAll(diaryAnalyses);

        savedDiaryAnalyses.sort((a, b) -> b.getWeight() - a.getWeight());

        DiaryAnalysis maxWeightEmotion = savedDiaryAnalyses.get(0);

        diaryPage.successAnalysis(maxWeightEmotion.getEmotionCode());
    }
}
