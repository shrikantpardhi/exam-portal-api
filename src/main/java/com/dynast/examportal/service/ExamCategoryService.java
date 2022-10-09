package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamCategoryDto;

public interface ExamCategoryService {
    Iterable<ExamCategoryDto> findAll();

    ExamCategoryDto findById(String examCategoryId);

    ExamCategoryDto create(ExamCategoryDto examCategory);

    ExamCategoryDto update(ExamCategoryDto examCategory);

    void delete(String examCategoryId);
}
