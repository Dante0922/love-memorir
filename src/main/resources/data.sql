-- Emotion 테이블에 질문 데이터 삽입
-- STABILITY
INSERT INTO question (emotion, content) VALUES (0, '어떤 것이 오늘 당신을 안정되게 느끼게 했나요?');
INSERT INTO question (emotion, content) VALUES (0, '오늘 하루, 어떤 순간이 마음을 편안하게 했나요?');

-- HAPPINESS
INSERT INTO question (emotion, content) VALUES (1, '오늘 가장 행복했던 순간은 언제였나요?');
INSERT INTO question (emotion, content) VALUES (1, '어떤 사건이 오늘 당신을 웃게 만들었나요?');

-- ROMANCE
INSERT INTO question (emotion, content) VALUES (2, '최근 느낀 설렘이 있나요? 그것에 대해 말해주세요.');
INSERT INTO question (emotion, content) VALUES (2, '어떤 사람이나 상황이 당신의 마음을 두근거리게 했나요?');

-- SADNESS
INSERT INTO question (emotion, content) VALUES (3, '최근에 슬픔을 느낀 경험은 무엇인가요?');
INSERT INTO question (emotion, content) VALUES (3, '어떤 일이 당신을 가장 슬프게 했나요?');

-- ANGER
INSERT INTO question (emotion, content) VALUES (4, '최근에 화가 난 사건이 있었나요? 그 사건에 대해 설명해주세요.');
INSERT INTO question (emotion, content) VALUES (4, '어떤 것이 당신을 가장 화나게 했나요?');


--
-- INSERT INTO diary_analysis (diary_analysis_id, emotion_code, weight, diary_page_id) VALUES (1, 1, 5, 1);
-- INSERT INTO diary_analysis (diary_analysis_id, emotion_code, weight, diary_page_id) VALUES (2, 2, 3, 2);
-- INSERT INTO diary_analysis (diary_analysis_id, emotion_code, weight, diary_page_id) VALUES (3, 3, 7, 3);