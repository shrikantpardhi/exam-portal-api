package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.QuestionDto;
import com.dynast.examportal.repository.AnswerRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.repository.QuestionTypeRepository;
import com.dynast.examportal.service.QuestionService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final ExamRepository examRepository;

    private final QuestionTypeRepository questionTypeRepository;

    private final AnswerRepository answerRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public QuestionServiceImpl(QuestionRepository questionRepository, ExamRepository examRepository, QuestionTypeRepository questionTypeRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
        this.questionTypeRepository = questionTypeRepository;
        this.answerRepository = answerRepository;
    }


    @Override
    public QuestionDto create(QuestionDto question) {
        return null;
    }

    @Override
    public QuestionDto update(QuestionDto question) {
        return null;
    }

    @Override
    public void deleteById(String questionId) {

    }

    @Override
    public QuestionDto findQuestionById(String questionId) {
        return null;
    }

    @Override
    public Iterable<QuestionDto> findByExam(String examId) {
        return null;
    }

    @Override
    public Iterable<QuestionDto> findByTag(String examId) {
        return null;
    }

    @Override
    public Iterable<QuestionDto> getAllQuestion() {
        return null;
    }
}
