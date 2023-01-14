package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuizDto;
import lombok.NonNull;

import java.util.List;

public interface QuizService {
    QuizDto create(@NonNull QuizDto quizDto);

    QuizDto update(@NonNull QuizDto quizDto);

    QuizDto getQuizById(@NonNull String quizId);

    List<QuizDto> getQuizByUser(@NonNull String userId);

    List<QuizDto> fetchAll();

    List<QuizDto> fetchAllByEducator(@NonNull String userId);

    QuizDto fetchById(@NonNull String quizId);

    List<QuizDto> fetchAllByExams(@NonNull String examIds);

}

