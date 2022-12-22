package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.QAResponse;
import com.dynast.examportal.dto.QAResponses;
import com.dynast.examportal.dto.QuizDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Quiz;
import com.dynast.examportal.model.Result;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuizRepository;
import com.dynast.examportal.repository.ResultRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.service.QuizService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class QuizServiceImpl implements QuizService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuizServiceImpl.class);
    private final QuizRepository quizRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public QuizServiceImpl(QuizRepository quizRepository, ExamRepository examRepository, UserRepository userRepository, ResultRepository resultRepository) {
        this.quizRepository = quizRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    /*
     * create when user start quiz.
     * on create there is no response. */
    @Override
    public QuizDto create(@NonNull QuizDto quizDto) throws IOException {
        LOGGER.info("create quiz first time. exam name: {}", quizDto.getExam().getExamTitle());
        Exam exam = examRepository.findById(quizDto.getExam().getExamId()).get();
        User user = userRepository.findById(quizDto.getUser().getUserId()).get();
        Quiz quiz = mapper.convertValue(quizDto, Quiz.class);
        quiz.setUser(user);
        quiz.setExam(exam);
        quiz = quizRepository.save(quiz);
        return mapper.convertValue(quiz, QuizDto.class);
    }

    @Override
    public QuizDto update(@NonNull QuizDto quizDto) {
        LOGGER.info("inside update quiz id: {}", quizDto.getQuizId());
        Quiz quiz = quizRepository.findById(quizDto.getQuizId()).get();
        Result result = new Result();
        QAResponses qaResponses = mapper.convertValue(quizDto.getQaResponses(), QAResponses.class);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            mapper.writeValue(out, qaResponses);
        } catch (IOException ex) {
            LOGGER.error("Unable to parse result data. {}", ex.getMessage());
        }
        // save response in result entity
        result.setQuiz(quiz);
        result.setResponse(out.toByteArray());
        result = resultRepository.save(result);

        // calculate mark details
        setMarkDetail(quiz, qaResponses);

        quiz.setSubmitted(quizDto.getSubmitted());
        quiz.setDuration(getDuration(quiz));
        quiz.setResult(result);
        quiz = quizRepository.save(quiz);
        return mapper.convertValue(quiz, QuizDto.class);
    }

    private void setMarkDetail(Quiz quiz, QAResponses qaResponses) {
        int correctCount = 0;
        float mark = 0;
        float negativeMark = 0;
        int inCorrectCount = 0;
        for (QAResponse qaResponse : qaResponses.getQaResponses()) {
            if (qaResponse.getCorrectAnswer().getId().equals(qaResponse.getSelectedAnswer().getAnswer())) {
                mark = qaResponse.getQuestion().getMark();
                correctCount += 1;
            } else if (qaResponse.getQuestion().getIsNegativeAllowed() && !qaResponse.getCorrectAnswer().getId().equals(qaResponse.getSelectedAnswer().getAnswer())) {
                negativeMark = (1 / 3) * qaResponse.getQuestion().getMark();
                inCorrectCount += 1;
            }
        }
        quiz.setCorrectCount(correctCount);
        quiz.setInCorrectCount(inCorrectCount);
        quiz.setObtained(String.valueOf(mark - negativeMark));
        quiz.setNegativeObtained(String.valueOf(negativeMark));
    }

    @Override
    public QuizDto getQuizById(@NonNull String quizId) {
        LOGGER.info("getQuizById: {}", quizId);
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> {
            LOGGER.error("No quiz found. {}", quizId);
            throw new NotFoundException("No quiz found");
        });
        return mapper.convertValue(quiz, QuizDto.class);
    }

    @Override
    public List<QuizDto> getQuizByUser(@NonNull String userId) {
        return null;
    }

    private String getDuration(Quiz quiz) {
        long diff = new Date().getTime() - quiz.getCreated().getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        return diffHours + ":" + diffMinutes + ":" + diffSeconds;
    }
}
