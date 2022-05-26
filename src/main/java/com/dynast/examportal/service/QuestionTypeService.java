package com.dynast.examportal.service;

import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.QuestionType;
import com.dynast.examportal.repository.QuestionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionTypeService {
    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    public Iterable<QuestionType> getAll() {
        return questionTypeRepository.findAll();
    }

    public QuestionType update(QuestionType questionType) {
        return questionTypeRepository.findById(questionType.getQuestionTypeId())
                .map(questionType1 -> {
                    return questionTypeRepository.save(questionType1);
                }).orElseThrow(
                        () -> new UnprocessableEntityException("Unable to process Question Type " + questionType.getQuestionTypeName())
                );
    }

    public QuestionType deleteById(String questionTypeId) {
        Optional<QuestionType> questionType = Optional.ofNullable(questionTypeRepository.findById(questionTypeId)
                .orElseThrow(() -> new NotFoundException("Could not find Exam Category!")));
        questionTypeRepository.delete(questionType.get());
        return questionType.get();
    }

    public QuestionType getById(String questionTypeId) {
        return questionTypeRepository.findById(questionTypeId).orElseThrow(
                () -> new NotFoundException("Could not find Question Type")
        );
    }

    public QuestionType create(QuestionType examCategory) {
        return questionTypeRepository.save(examCategory);
    }
}
