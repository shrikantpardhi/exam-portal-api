package com.dynast.examportal.service;

import com.dynast.examportal.dto.AnswerDto;

public interface AnswerService {
    Iterable<AnswerDto> getAllByQuestion(String questionId);

    AnswerDto getByAnswerId(String answerId);

    AnswerDto create(AnswerDto answer);

    AnswerDto update(AnswerDto answer);

    void delete(String answerId);
}
