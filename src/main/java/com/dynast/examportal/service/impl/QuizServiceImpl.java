package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.QAResponse;
import com.dynast.examportal.dto.QAResponses;
import com.dynast.examportal.dto.QuizDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Quiz;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuizRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.service.QuizService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class QuizServiceImpl implements QuizService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuizServiceImpl.class);
    private final QuizRepository quizRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public QuizServiceImpl(QuizRepository quizRepository, ExamRepository examRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    /*
     * create when user start quiz.
     * on create there is no response. */
    @Override
    public QuizDto create(@NonNull QuizDto quizDto) {
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
        QAResponses qaResponses = mapper.convertValue(quizDto.getQaResponses(), QAResponses.class);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            mapper.writeValue(out, qaResponses);
        } catch (IOException ex) {
            LOGGER.error("Unable to parse result data. {}", ex.getMessage());
        }

        // calculate mark details
        setMarkDetail(quiz, qaResponses);

        quiz.setSubmitted(quizDto.getSubmitted());
        quiz.setDuration(getDuration(quiz));
        quiz.setResponse(out.toByteArray());
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
        LOGGER.info("getQuizByUser: {}", userId);
        User user = userRepository.findById(userId).get();
        List<Quiz> quizList = quizRepository.findAllByUser(user).orElseThrow(() -> {
            LOGGER.error("No quiz found. {}", userId);
            throw new NotFoundException("No quiz found");
        });
        return quizList.stream().map(quiz -> mapper.convertValue(quiz, QuizDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<QuizDto> fetchAll() {
        LOGGER.info("inside fetchAll");
        Iterable<Quiz> all = quizRepository.findAll();
        List<QuizDto> quizDtos = StreamSupport.stream(all.spliterator(), false)
                .map(quiz -> mapper.convertValue(quiz, QuizDto.class))
                .collect(Collectors.toList());
        return quizDtos;
    }

    @Override
    public List<QuizDto> fetchAllByEducator(@NonNull String userId) {
        LOGGER.info("inside fetchAllByEducator: {}", userId);
        User user = userRepository.findById(userId).get();
        Set<EducatorCode> codes = user.getEducatorCodes();
        List<Exam> exams = examRepository.findAllByEducatorCodeIn(codes);
        List<Quiz> quizList = quizRepository.findAllByExamIn(exams);
        return quizList.stream().map(quiz -> mapper.convertValue(quiz, QuizDto.class)).collect(Collectors.toList());
    }

    @Override
    public QuizDto fetchById(@NonNull String quizId) {
        LOGGER.info("inside fetchById: {}", quizId);
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new NotFoundException("Quiz not found"));
        return mapper.convertValue(quiz, QuizDto.class);
    }

    @Override
    public List<QuizDto> fetchAllByExams(@NonNull String examIds) {
        LOGGER.info("inside fetchAllByExams: {}", examIds);
        List<String> ids = Arrays.asList(examIds.split(" , "));
        Iterable<Exam> exams = examRepository.findAllById(ids);
        List<Quiz> quizList = quizRepository.findAllByExamIn(StreamSupport.stream(exams.spliterator(), false).collect(Collectors.toList()));
        List<QuizDto> quizDtos = quizList.stream().map(quiz -> mapper.convertValue(quiz, QuizDto.class)).collect(Collectors.toList());
        return quizDtos;
    }

    private String getDuration(Quiz quiz) {
        long diff = new Date().getTime() - quiz.getCreated().getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        return diffHours + ":" + diffMinutes + ":" + diffSeconds;
    }
}
