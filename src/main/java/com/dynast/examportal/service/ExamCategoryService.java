package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamCategoryDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.repository.ExamCategoryRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamCategoryService {
    private final ExamCategoryRepository examCategoryRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ExamCategoryService(ExamCategoryRepository examCategoryRepository) {
        this.examCategoryRepository = examCategoryRepository;
    }

    public void delete(String examCategoryId) throws DataBaseException {
        examCategoryRepository.findById(examCategoryId).ifPresent(
                examCategoryRepository::delete
        );
    }

    public ExamCategoryDto update(ExamCategoryDto examCategory) {
        return examCategoryRepository.findById(examCategory.getExamCategoryId()).map(examCategory1 -> {
            examCategory1.setExamCategoryName(examCategory.getExamCategoryName());
            examCategory1.setIsPremium(examCategory.getIsPremium());
            examCategory1.setUpdatedBy(examCategory.getUpdatedBy());
            return mapper.convertValue(examCategoryRepository.save(examCategory1), ExamCategoryDto.class);
        }).orElseThrow(
                () -> new UnprocessableEntityException("Unable to update Exam category" + examCategory.getExamCategoryName())
        );
    }

    public ExamCategoryDto create(ExamCategoryDto examCategory) {
        ExamCategory examCategory1 = mapper.convertValue(examCategory, ExamCategory.class);
        return mapper.convertValue(examCategoryRepository.save(examCategory1), ExamCategoryDto.class);
    }

    public ExamCategoryDto findById(String examCategoryId) {
        ExamCategory examCategory = examCategoryRepository.findById(examCategoryId)
                .orElseThrow(() -> new NotFoundException("Could not find Exam Category!"));
        return mapper.convertValue(examCategory, ExamCategoryDto.class);
    }

    public Iterable<ExamCategoryDto> findAll() {
        Iterable<ExamCategory> examCategories = examCategoryRepository.findAll();
        List<ExamCategoryDto> examCategoryDtoList = new ArrayList<>();
        examCategories.forEach(
                examCategory ->
                        examCategoryDtoList.add(mapper.convertValue(examCategory, ExamCategoryDto.class))
        );
        return examCategoryDtoList;
    }
}
