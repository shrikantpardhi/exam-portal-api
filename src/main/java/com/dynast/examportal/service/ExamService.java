package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamDto;

import java.util.List;
import java.util.Set;

public interface ExamService {
    ExamDto create(ExamDto examDto);

    ExamDto update(ExamDto examDto);

    void delete(String examId);

    List<ExamDto> getAll();

    List<ExamDto> getByEducatorCode(String code);

    List<ExamDto> getByUser(String userId);

    ExamDto getByExamId(String examId);

    ExamDto changeStatus(String examId);

    List<ExamDto> getByEducatorCodes(Set<String> educatorCodes);

    List<ExamDto> getByUserEducatorCodes(String userId);

    List<ExamDto> getByTag(String name);

    List<ExamDto> getByTags(List<String> names);

}
