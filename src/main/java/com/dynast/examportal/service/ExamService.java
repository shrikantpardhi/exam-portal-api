package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamDto;

public interface ExamService {
    ExamDto create(ExamDto exam);

    ExamDto update(ExamDto exam);

    void delete(String examId);

    Iterable<ExamDto> getAll();

    ExamDto getOne(String examId);
}
