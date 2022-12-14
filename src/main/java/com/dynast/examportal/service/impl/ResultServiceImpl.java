package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.ResultPageDto;
import com.dynast.examportal.dto.UserResultDto;
import com.dynast.examportal.repository.AnswerRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.repository.UserResultRepository;
import com.dynast.examportal.service.ResultService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    private final UserResultRepository userResultRepository;

    private final UserRepository userRepository;

    private final ExamRepository examRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ResultServiceImpl(UserResultRepository userResultRepository, UserRepository userRepository, ExamRepository examRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.userResultRepository = userResultRepository;
        this.userRepository = userRepository;
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }


    @Override
    public Iterable<UserResultDto> getAll() {
        return null;
    }

    @Override
    public Iterable<UserResultDto> getAllResultByUser(String userId) {
        return null;
    }

    @Override
    public ResultPageDto getResultPageByUser(String userId, String resultId) {
        return null;
    }

    @Override
    public UserResultDto create(UserResultDto userResult) {
        return null;
    }

    @Override
    public UserResultDto update(UserResultDto userResult) {
        return null;
    }
}
