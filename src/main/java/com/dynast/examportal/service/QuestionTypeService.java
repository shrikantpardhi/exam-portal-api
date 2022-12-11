package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuestionTypeDto;

public interface QuestionTypeService {

    QuestionTypeDto update(QuestionTypeDto questionType);

    QuestionTypeDto create(QuestionTypeDto questionType);

    QuestionTypeDto getById(String questionTypeId);

    Iterable<QuestionTypeDto> getAll();

    void deleteById(String questionTypeId);
}
