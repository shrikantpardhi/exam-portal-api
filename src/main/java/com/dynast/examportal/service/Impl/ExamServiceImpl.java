package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.service.ExamService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;


    private final QuestionRepository questionRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public ExamDto create(ExamDto exam) {
        return null;
    }

    @Override
    public ExamDto update(ExamDto exam) {
        return null;
    }

    @Override
    public void delete(String examId) {

    }

    @Override
    public Iterable<ExamDto> getAll() {
        return null;
    }

    @Override
    public ExamDto getOne(String examId) {
        return null;
    }
}
