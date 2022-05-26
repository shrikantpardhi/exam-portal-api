package com.dynast.examportal.service;

import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.repository.ExamCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamCategoryService {
    @Autowired
    private ExamCategoryRepository examCategoryRepository;

    public ExamCategory delete(String examCategoryId) {
        Optional<ExamCategory> examCategory = Optional.ofNullable(examCategoryRepository.findById(examCategoryId)
                .orElseThrow(() -> new NotFoundException("Could not find Exam Category!")));
        examCategoryRepository.delete(examCategory.get());
        return examCategory.get();
    }

    public ExamCategory update(ExamCategory examCategory) {
        return examCategoryRepository.findById(examCategory.getExamCategoryId()).map(examCategory1 -> {
            return examCategoryRepository.save(examCategory);
        }).orElseThrow(
                () -> new UnprocessableEntityException("Unable to update Exam category" + examCategory.getExamCategoryName())
        );
    }

    public ExamCategory create(ExamCategory examCategory) {
        return examCategoryRepository.save(examCategory);
    }

    public ExamCategory findById(String examCategoryId) {
        return examCategoryRepository.findById(examCategoryId)
                .orElseThrow(() -> new NotFoundException("Could not find Exam Category!"));
    }

    public Iterable<ExamCategory> findAll() {
        return examCategoryRepository.findAll();
    }
}
