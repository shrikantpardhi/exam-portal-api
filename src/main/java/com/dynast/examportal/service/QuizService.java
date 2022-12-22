package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuizDto;
import lombok.NonNull;

import java.io.IOException;
import java.util.List;

public interface QuizService {
    QuizDto create(@NonNull QuizDto quizDto) throws IOException;
    QuizDto update(@NonNull QuizDto quizDto);
    QuizDto getQuizById(@NonNull String quizId);
    List<QuizDto> getQuizByUser(@NonNull String userId);
}
