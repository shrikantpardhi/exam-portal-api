package com.dynast.examportal.service;

import com.dynast.examportal.dto.EducatorCodeDto;
import com.dynast.examportal.dto.ExamDto;

import java.util.List;

public interface ExamService {
    ExamDto create(ExamDto examDto);

    ExamDto update(ExamDto examDto);

    void delete(String examId);

    List<ExamDto> getAll();

    List<ExamDto> getByEducatorCode(String code);

    List<ExamDto> getByUser(String userId);

    ExamDto getByExamId(String examId);

    ExamDto changeStatus(String examId);

    List<ExamDto> getByEducatorCodes(List<EducatorCodeDto> educatorCodeDtos);

    List<ExamDto> getByUserEducatorCodes(String userId);

}
