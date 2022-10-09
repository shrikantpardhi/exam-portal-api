package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuestionTypeDto;

public interface QuestionTypeService {
    void deleteById(String questionTypeId);

    QuestionTypeDto update(QuestionTypeDto questionType);

    QuestionTypeDto create(QuestionTypeDto questionType);

    QuestionTypeDto getById(String questionTypeId);

    Iterable<QuestionTypeDto> getAll();
}
