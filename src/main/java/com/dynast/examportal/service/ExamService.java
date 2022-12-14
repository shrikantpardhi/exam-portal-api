package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamDto;

import java.util.List;

public interface ExamService {
    ExamDto create(ExamDto examDto);

    ExamDto update(ExamDto examDto);

    void delete(String examId);

    List<ExamDto> getAll();

    ExamDto findByExamId(String examId);

    ExamDto changeStatus(String examId);

//    List<ExamDto>
}
