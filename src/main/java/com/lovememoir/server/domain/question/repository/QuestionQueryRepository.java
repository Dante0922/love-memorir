package com.lovememoir.server.domain.question.repository;

import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.question.ContentType;
import com.lovememoir.server.domain.question.QQuestion;
import com.lovememoir.server.domain.question.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public List<Question> findQuestionByEmotion(Emotion emotion) {
        QQuestion question = QQuestion.question;
        return jpaQueryFactory
            .selectFrom(question)
            .where(question.emotion.eq(emotion)
                .and(question.contentType.eq(ContentType.QUESTION)))
            .fetch();
    }

    @Transactional(readOnly = true)
    public List<Question> findConversationByEmotion(Emotion emotion) {
        QQuestion question = QQuestion.question;
        return jpaQueryFactory
            .selectFrom(question)
            .where(question.emotion.eq(emotion)
                .and(question.contentType.eq(ContentType.CONVERSATION)))
            .fetch();
    }
}
