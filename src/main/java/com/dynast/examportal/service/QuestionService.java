package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuestionDto;

public interface QuestionService {
    QuestionDto create(QuestionDto question);

    QuestionDto update(QuestionDto question);

    void deleteById(String questionId);

    QuestionDto findQuestionById(String questionId);

    Iterable<QuestionDto> findByExam(String examId);
    Iterable<QuestionDto> findByTag(String examId);

    Iterable<QuestionDto> getAllQuestion();
}
