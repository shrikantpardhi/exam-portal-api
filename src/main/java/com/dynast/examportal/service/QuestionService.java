package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.dto.QuestionDto;
import com.dynast.examportal.dto.QuestionTypeDto;
import com.dynast.examportal.dto.SubjectDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.model.QuestionType;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.repository.QuestionTypeRepository;
import com.dynast.examportal.repository.SubjectRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final SubjectRepository subjectRepository;

    private final ExamRepository examRepository;

    private final QuestionTypeRepository questionTypeRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public QuestionService(QuestionRepository questionRepository, SubjectRepository subjectRepository, ExamRepository examRepository, QuestionTypeRepository questionTypeRepository) {
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
        this.examRepository = examRepository;
        this.questionTypeRepository = questionTypeRepository;
    }

    public QuestionDto create(QuestionDto questionDto) {
        Subject subject = getSubject(questionDto);
        Exam exam = getExam(questionDto);
        QuestionType questionType = getQuestionType(questionDto);
        Question question = createQuestion(subject, exam, questionType, questionDto);
        return toQuestionDto(questionRepository.save(question));
    }

    public QuestionDto update(QuestionDto questionDto) {
        Subject subject = getSubject(questionDto);
        Exam exam = getExam(questionDto);
        QuestionType questionType = getQuestionType(questionDto);
        return questionRepository.findById(questionDto.getQuestionId()).map(
                question1 -> {
                    question1 = createQuestion(subject, exam, questionType, questionDto);
                    return toQuestionDto(questionRepository.save(question1));
                }
        ).orElseThrow(() -> new UnprocessableEntityException("Could not able to process request!"));
    }

    public void deleteById(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find Question!")
                );
        questionRepository.delete(question);
    }

    public QuestionDto findQuestionById(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find Question")
                );
        return toQuestionDto(question);
    }

    public Iterable<QuestionDto> getAllQuestion() {
        Iterable<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        questions.forEach(
                question -> questionDtoList.add(toQuestionDto(question))
        );
        return questionDtoList;
    }

    public Iterable<QuestionDto> findByExam(String examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(
                () -> new NotFoundException("Could not find exam!")
        );
        Iterable<Question> questions = questionRepository.findByExam(exam);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        questions.forEach(
                question -> questionDtoList.add(toQuestionDto(question))
        );
        return questionDtoList;
    }

    public Iterable<QuestionDto> findBySubject(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Could not find subject!")
        );
        Iterable<Question> questions = questionRepository.findBySubject(subject);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        questions.forEach(
                question -> questionDtoList.add(toQuestionDto(question))
        );
        return questionDtoList;
    }

    private QuestionDto toQuestionDto(Question question) {
        QuestionDto questionDto = mapper.convertValue(question, QuestionDto.class);
        questionDto.setSubjectDto(mapper.convertValue(question.getSubject(), SubjectDto.class));
        questionDto.setQuestionTypeDto(mapper.convertValue(question.getQuestionType(), QuestionTypeDto.class));
        questionDto.setExamDto(mapper.convertValue(question.getExam(), ExamDto.class));
        return questionDto;
    }

    private Question createQuestion(Subject subject, Exam exam, QuestionType questionType, QuestionDto questionDto) {
        Question que = mapper.convertValue(questionDto, Question.class);
        que.setSubject(subject);
        que.setExam(exam);
        que.setQuestionType(questionType);
        return que;
    }

    private QuestionType getQuestionType(QuestionDto questionDto) {
        return questionTypeRepository.findById(questionDto.getQuestionTypeDto().getQuestionTypeId()).orElseThrow(
                () -> new NotFoundException("Could not find QuestionType")
        );
    }

    private Exam getExam(QuestionDto questionDto) {
        return examRepository.findById(questionDto.getExamDto().getExamId()).orElseThrow(
                () -> new NotFoundException("Could not find Exam")
        );
    }

    private Subject getSubject(QuestionDto questionDto) {
        return subjectRepository.findById(questionDto.getSubjectDto().getSubjectId()).orElseThrow(
                () -> new NotFoundException("Could not find Subject")
        );
    }
}
