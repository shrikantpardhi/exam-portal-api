package com.dynast.examportal.service.impl;

import com.dynast.examportal.dto.QuestionDto;
import com.dynast.examportal.exception.ExamNotFoundException;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.QuestionNotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Answer;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.model.Tag;
import com.dynast.examportal.repository.AnswerRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.repository.TagRepository;
import com.dynast.examportal.service.QuestionService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.dynast.examportal.util.QuestionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    private final ExamRepository examRepository;

    private final AnswerRepository answerRepository;

    private final TagRepository tagRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public QuestionServiceImpl(QuestionRepository questionRepository, ExamRepository examRepository, AnswerRepository answerRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
        this.answerRepository = answerRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public QuestionDto create(QuestionDto questionDto) {
        LOGGER.info("create question {}", questionDto.getQuestionTitle());
        Exam exam = examRepository.findById(questionDto.getExam().getExamId()).orElseThrow(() -> {
            LOGGER.error("No exam found. {}", questionDto.getExam().getExamId());
            throw new ExamNotFoundException("Exam not found");
        });
        Question question = mapper.convertValue(questionDto, Question.class);
        question.setExam(exam);
        Tag tag = tagRepository.findByName(questionDto.getTag().getName().toUpperCase())
                .orElseThrow(() -> {
                   LOGGER.error("No tag found. {}", questionDto.getTag().getName());
                   throw new NotFoundException("No tag found");
                });
        question.setTag(tag);
        // Get question type
        String questionType = getQuestionType(questionDto);
        if(Objects.isNull(questionType)) {
          LOGGER.error("Error: questionType is null. {}", questionDto.getQuestionType());
          throw new UnprocessableEntityException("No Question Type found.");
        }
        question.setQuestionType(questionType);
        // Get Answers from questionDTO
        List<Answer> answers = getAnswers(questionDto);
        Iterable<Answer> answers1 = answerRepository.saveAll(answers);
        List<Answer> answerList = StreamSupport.stream(answers1.spliterator(), false).collect(Collectors.toList());
        question.setAnswers(answerList);
        question = questionRepository.save(question);
        LOGGER.info("created question. {}", question.getQuestionId());
        return mapper.convertValue(question, QuestionDto.class);
    }


    @Override
    public QuestionDto update(QuestionDto questionDto) {
        LOGGER.info("inside update. {}", questionDto.getQuestionId());
        if(!questionRepository.findById(questionDto.getQuestionId()).isPresent()) {
            LOGGER.error("No question found. {}", questionDto.getQuestionId());
            throw new QuestionNotFoundException("Question not found.");
        }
        Tag tag = tagRepository.findByName(questionDto.getTag().getName().toUpperCase())
                .orElseThrow(() -> {
                    LOGGER.error("No tag found. {}", questionDto.getTag().getName());
                    throw new NotFoundException("No tag found");
                });
        // Get question type
        String questionType = getQuestionType(questionDto);
        if(Objects.isNull(questionType)) {
            LOGGER.error("Error: questionType is null. {}", questionDto.getQuestionType());
            throw new UnprocessableEntityException("No Question Type found.");
        }
        // Get Answers from questionDTO
        List<Answer> answers = getAnswers(questionDto);
        Iterable<Answer> answers1 = answerRepository.saveAll(answers);
        List<Answer> answerList = StreamSupport.stream(answers1.spliterator(), false).collect(Collectors.toList());
        Question question = questionRepository.findById(questionDto.getQuestionId())
                .map(que -> {
                    que.setQuestionTitle(questionDto.getQuestionTitle());
                    que.setQuestionDescription(questionDto.getQuestionDescription());
                    que.setTag(tag);
                    que.setQuestionAnswerDescription(questionDto.getQuestionAnswerDescription());
                    que.setIsNegativeAllowed(questionDto.getIsNegativeAllowed());
                    que.setQuestionImage(questionDto.getQuestionImage());
                    que.setQuestionMark(questionDto.getQuestionMark());
                    que.setAnswers(answers);
                    return questionRepository.save(que);
                }).orElseThrow(() -> {
                    LOGGER.error("Unable to update question. {}", questionDto.getQuestionId());
                    throw new UnprocessableEntityException("Unable to update question.");
                });
        LOGGER.info("updated question. {}", question.getQuestionId());
        return mapper.convertValue(question, QuestionDto.class);
    }

    @Override
    public void deleteById(String questionId) {
        LOGGER.info("delete by question id {}", questionId);
        questionRepository.deleteById(questionId);
    }

    @Override
    public QuestionDto findQuestionById(String questionId) {
        LOGGER.info("findQuestionById {}", questionId);
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException("Question not found."));
        return mapper.convertValue(question, QuestionDto.class);
    }

    @Override
    public Iterable<QuestionDto> findByExam(String examId) {
        LOGGER.info("find question by exam id {}", examId);
        Exam exam = examRepository.findById(examId).orElseThrow(() -> {
            LOGGER.error("No exam found. {}", examId);
            throw new ExamNotFoundException("Exam not found");
        });
        List<Question> questions = questionRepository.findByExam(exam);
        List<QuestionDto> questionDtos = questions.stream()
                .map(question -> mapper.convertValue(question, QuestionDto.class))
                .collect(Collectors.toList());
        LOGGER.info("examId {}, questions {}",examId, questions.size());
        return questionDtos;
    }

    @Override
    public Iterable<QuestionDto> findByTag(String name) {
        LOGGER.info("find question by tag name {}", name);
        Tag tag = tagRepository.findByName(name.toUpperCase())
                .orElseThrow(() -> {
                    LOGGER.error("No tag found. {}", name);
                    throw new NotFoundException("No tag found");
                });
        List<Question> questions = questionRepository.findByTag(tag);
        List<QuestionDto> questionDtos = questions.stream()
                .map(question -> mapper.convertValue(question, QuestionDto.class))
                .collect(Collectors.toList());
        LOGGER.info("tag {}, questions {}",name, questions.size());
        return questionDtos;
    }

    @Override
    public Iterable<QuestionDto> getAllQuestion() {
        LOGGER.info("in getAllQuestion");
        Iterable<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = StreamSupport.stream(questions.spliterator(), false)
                .map(question -> mapper.convertValue(question, QuestionDto.class))
                .collect(Collectors.toList());
        LOGGER.info("questions {}", questionDtos.size());
        return questionDtos;
    }

    @Override
    public EnumSet<QuestionType> getQuestionTypes() {
        LOGGER.info("in getQuestionTypes");
        EnumSet<QuestionType> questionTypes = EnumSet.allOf(QuestionType.class);
        return questionTypes;
    }

    private String getQuestionType(QuestionDto question) {
        String questionType = null;
        if (QuestionType.SINGLE.getDescription().equalsIgnoreCase(question.getQuestionType())) {
            questionType = QuestionType.SINGLE.getLabel();
        } else if (QuestionType.SINGLE.getDescription().equalsIgnoreCase(question.getQuestionType())) {
            questionType = QuestionType.SINGLE.getLabel();
        } else if (QuestionType.SINGLE.getDescription().equalsIgnoreCase(question.getQuestionType())) {
            questionType = QuestionType.SINGLE.getLabel();
        }
        return questionType;
    }

    private List<Answer> getAnswers(QuestionDto questionDto) {
        List<Answer> answers = questionDto.getAnswers().stream()
                .map(answerDto -> mapper.convertValue(answerDto, Answer.class))
                .collect(Collectors.toList());
        return answers;
    }

}
