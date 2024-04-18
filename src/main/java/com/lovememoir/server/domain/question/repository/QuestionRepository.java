package com.lovememoir.server.domain.question.repository;

import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByEmotion(Emotion emotion);
}
