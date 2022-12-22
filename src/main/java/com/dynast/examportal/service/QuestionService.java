package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuestionDto;
import com.dynast.examportal.util.QuestionType;

import java.util.EnumSet;

public interface QuestionService {
    QuestionDto create(QuestionDto question);

    QuestionDto update(QuestionDto question);

    void deleteById(String questionId);

    QuestionDto findQuestionById(String questionId);

    Iterable<QuestionDto> findByExam(String examId);

    Iterable<QuestionDto> findByTag(String name);

    Iterable<QuestionDto> getAllQuestion();

    EnumSet<QuestionType> getQuestionTypes();
}
