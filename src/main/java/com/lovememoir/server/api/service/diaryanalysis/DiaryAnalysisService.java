package com.lovememoir.server.api.service.diaryanalysis;

import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DiaryAnalysisService {

    private final DiaryAnalysisRepository diaryAnalysisRepository;
    private final DiaryPageRepository diaryPageRepository;
    private final OpenAiChatClient chatClient;
    private final OpenAiApiService openAiApiService;

    public void diaryAnalysis(final Long diaryPageId) throws ParseException {
        DiaryPage diaryPage = diaryPageRepository.findById(diaryPageId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY_PAGE));

        String prompt = OpenaiPrompt.generatePrompt(diaryPage.getContent());

        String response = "";

        try{
            response = openAiApiService.callGpt4Api(prompt);

        } catch (Exception e){
            System.out.println(e);
        }

        JSONObject json = null;
        try{
            JSONParser jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
            Object obj =  jsonParser.parse(response);
            json = (JSONObject) obj;
        } catch (ParseException e) {
            log.error("JSON 파싱 실패", e);
        } catch (Exception e) {
            log.error("무슨 다른 에러가..?", e);
        }

        List<DiaryAnalysis> diaryAnalyses = new ArrayList<>();

        for (Emotion emotion : Emotion.values()) {
            System.out.println(emotion);
            int emotionCode = emotion.getCode();
            Object weightObj = json.get(emotion.name());
            int weight = 0;
            if (weightObj != null) {
                weight = ((Long) weightObj).intValue();
            }
            diaryAnalyses.add(DiaryAnalysis.create(emotionCode, weight, diaryPage));
        }



        List<DiaryAnalysis> savedDiaryAnalyses = diaryAnalysisRepository.saveAll(diaryAnalyses);

        savedDiaryAnalyses.sort((a, b) -> b.getWeight() - a.getWeight());

        DiaryAnalysis maxWeightEmotion = savedDiaryAnalyses.get(0);

        diaryPage.successAnalysis(maxWeightEmotion.getEmotionCode());
    }
}
