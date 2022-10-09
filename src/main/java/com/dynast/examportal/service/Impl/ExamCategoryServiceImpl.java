package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.ExamCategoryDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.repository.ExamCategoryRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.service.ExamCategoryService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamCategoryServiceImpl implements ExamCategoryService {
    private final ExamCategoryRepository examCategoryRepository;
    private final ExamRepository examRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ExamCategoryServiceImpl(ExamCategoryRepository examCategoryRepository, ExamRepository examRepository) {
        this.examCategoryRepository = examCategoryRepository;
        this.examRepository = examRepository;
    }

    @Override
    public void delete(String examCategoryId) throws DataBaseException {
        examCategoryRepository.findById(examCategoryId).ifPresent(
                examCategoryRepository::delete
        );
    }

    @Override
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

    @Override
    public ExamCategoryDto create(ExamCategoryDto examCategory) {
        ExamCategory examCategory1 = mapper.convertValue(examCategory, ExamCategory.class);
        return mapper.convertValue(examCategoryRepository.save(examCategory1), ExamCategoryDto.class);
    }

    @Override
    public ExamCategoryDto findById(String examCategoryId) {
        ExamCategory examCategory = examCategoryRepository.findById(examCategoryId)
                .orElseThrow(() -> new NotFoundException("Could not find Exam Category!"));
        ExamCategoryDto examCategoryDto = mapper.convertValue(examCategory, ExamCategoryDto.class);
        examCategoryDto.setTotalExams(examRepository.countByExamCategory(examCategory));
        return examCategoryDto;
    }

    @Override
    public Iterable<ExamCategoryDto> findAll() {
        Iterable<ExamCategory> examCategories = examCategoryRepository.findAll();
        List<ExamCategoryDto> examCategoryDtoList = new ArrayList<>();
        examCategories.forEach(
                examCategory ->
                {
                    ExamCategoryDto examCategoryDto = mapper.convertValue(examCategory, ExamCategoryDto.class);
                    examCategoryDto.setTotalExams(examRepository.countByExamCategory(examCategory));
                    examCategoryDtoList.add(examCategoryDto);
                }
        );
        return examCategoryDtoList;
    }
}
