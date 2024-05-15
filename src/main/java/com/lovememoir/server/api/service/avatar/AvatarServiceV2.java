package com.lovememoir.server.api.service.avatar;


import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.api.service.RedisService;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.AvatarQueryRepository;
import com.lovememoir.server.domain.avatar.repository.AvatarRepository;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisQueryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import com.lovememoir.server.domain.question.Question;
import com.lovememoir.server.domain.question.repository.QuestionRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_MEMBER;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AvatarServiceV2 {

    private final MemberRepository memberRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarQueryRepository avatarQueryRepository;
    private final QuestionRepository questionRepository;
    private final DiaryAnalysisQueryRepository diaryAnalysisQueryRepository;
    private final RedisService redisService;

    public AvatarResponse createAvatar(Member member) {

        Emotion emotion = Emotion.STABILITY;
        String question = "만나서 반가워요!";

        Avatar avatar = Avatar.create(emotion, question, member);
        avatarRepository.save(avatar);
        return AvatarResponse.of(avatar);
    }

    public AvatarRefreshResponse refreshAvatar(String providerId) {

        Member member = getMember(providerId);
        Long memberId = member.getId();

        Map.Entry<Integer, Double> highestEmotion = getRecentHighestEmotion(member);
        Avatar avatar = refreshMemberAvatar(highestEmotion, memberId);

        return AvatarRefreshResponse.of(avatar);
    }

    private Avatar refreshMemberAvatar(Map.Entry<Integer, Double> highestEmotion, Long memberId) {
        Integer emotionCode = highestEmotion.getKey();
        Emotion emotion = Emotion.fromCode(emotionCode);
        Question selectedQuestion = selectQuestion(emotion, memberId);

        Avatar avatar = avatarQueryRepository.findByMemberId(memberId);
        avatar.modified(emotion, selectedQuestion.getContent());
        return avatar;
    }

    private Question selectQuestion(Emotion emotion, Long memberId) {

        List<Question> allQuestions = questionRepository.findByEmotion(emotion);
        List<Question> availableQuestions = filterAvailableQuestions(memberId, allQuestions);
        Question selectedQuestion = selectRandomQuestion(availableQuestions);
        saveQuestionInRedis(memberId, selectedQuestion);
        return selectedQuestion;
    }

    private List<Question> filterAvailableQuestions(Long memberId, List<Question> allQuestions) {
        return allQuestions.stream()
            .filter(question -> redisService.find(redisService.generateAvatarKey(memberId, question.getId())) == null)
            .toList();
    }

    private static Question selectRandomQuestion(List<Question> availableQuestions) {
        return availableQuestions.isEmpty() ?
            Question.builder().emotion(Emotion.STABILITY).content("항복하길 바래요").build() :
            availableQuestions.get(new Random().nextInt(availableQuestions.size()));
    }

    private void saveQuestionInRedis(Long memberId, Question selectedQuestion) {
        redisService.saveWith7DaysExpiration(redisService.generateAvatarKey(memberId, selectedQuestion.getId()), "AA");
    }

    private Map.Entry<Integer, Double> getRecentHighestEmotion(Member member) {
        Optional<List<DiaryAnalysis>> analyses = diaryAnalysisQueryRepository.findTop3RecentAnalysesByMemberId(member.getId());

        if (analyses.isEmpty() || analyses.get().isEmpty()) {
            return new AbstractMap.SimpleEntry<>(Emotion.STABILITY.getCode(), 0.0);
        }
        Map<Integer, Double> emotionScores = analyses.get().stream()
            .collect(Collectors.groupingBy(DiaryAnalysis::getEmotionCode,
                Collectors.averagingDouble(DiaryAnalysis::getWeight)));

        return Collections.max(emotionScores.entrySet(), Map.Entry.comparingByValue());
    }

    private Member getMember(String providerId) {
        return memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
    }

}
