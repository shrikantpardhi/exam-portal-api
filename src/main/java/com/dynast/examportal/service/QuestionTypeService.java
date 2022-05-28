package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuestionTypeDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.QuestionType;
import com.dynast.examportal.repository.QuestionTypeRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionTypeService {
    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public Iterable<QuestionTypeDto> getAll() {
        Iterable<QuestionType> questionTypes = questionTypeRepository.findAll();
        List<QuestionTypeDto> questionTypeDtoList = new ArrayList<>();
        questionTypes.forEach(
                questionType -> questionTypeDtoList.add(mapper.convertValue(questionType, QuestionTypeDto.class))
        );
        return questionTypeDtoList;
    }

    public QuestionTypeDto update(QuestionTypeDto questionType) {
        QuestionType qType = questionTypeRepository.findById(questionType.getQuestionTypeId())
                .map(questionType1 -> questionTypeRepository.save(questionType1)).orElseThrow(
                        () -> new UnprocessableEntityException("Unable to process Question Type " + questionType.getQuestionTypeName())
                );
        return mapper.convertValue(qType, QuestionTypeDto.class);
    }

    public void deleteById(String questionTypeId) {
        Optional<QuestionType> questionType = Optional.ofNullable(questionTypeRepository.findById(questionTypeId)
                .orElseThrow(() -> new NotFoundException("Could not find Exam Category!")));
        questionTypeRepository.delete(questionType.get());
    }

    public QuestionTypeDto getById(String questionTypeId) {
        QuestionType questionType = questionTypeRepository.findById(questionTypeId).orElseThrow(
                () -> new NotFoundException("Could not find Question Type")
        );
        return mapper.convertValue(questionType, QuestionTypeDto.class);
    }

    public QuestionTypeDto create(QuestionTypeDto examCategory) {
        QuestionType qType = mapper.convertValue(examCategory, QuestionType.class);
        return mapper.convertValue(questionTypeRepository.save(qType), QuestionTypeDto.class);
    }
}
