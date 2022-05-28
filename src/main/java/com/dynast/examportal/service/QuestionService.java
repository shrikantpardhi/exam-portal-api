package com.dynast.examportal.service;

import com.dynast.examportal.dto.QuestionDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public QuestionDto create(QuestionDto question) {
        Optional<Subject> subject = subjectRepository.findById(question.getSubjectDto().getSubjectId());
        Optional<Exam> exam = examRepository.findById(question.getExamDto().getExamId());
        Optional<QuestionType> questionType = questionTypeRepository.findById(question.getQuestionTypeDto().getQuestionTypeId());
        Question que = mapper.convertValue(question, Question.class);
        que.setSubject(subject.get());
        que.setExam(exam.get());
        que.setQuestionType(questionType.get());
        return mapper.convertValue(questionRepository.save(que), QuestionDto.class);
    }

    public QuestionDto update(QuestionDto question) {
        Optional<Subject> subject = subjectRepository.findById(question.getSubjectDto().getSubjectId());
        Optional<Exam> exam = examRepository.findById(question.getExamDto().getExamId());
        Optional<QuestionType> questionType = questionTypeRepository.findById(question.getQuestionTypeDto().getQuestionTypeId());
        return questionRepository.findById(question.getQuestionId()).map(
                question1 -> {
                    question1.setExam(exam.get());
                    question1.setSubject(subject.get());
                    question1.setQuestionType(questionType.get());
                    question1.setQuestionTitle(question.getQuestionTitle());
                    question1.setQuestionDescription(question.getQuestionDescription());
                    question1.setQuestionAnswerDescription(question.getQuestionAnswerDescription());
                    question1.setQuestionImage(question.getQuestionImage());
                    question1.setQuestionAnswerDescriptionImage(question.getQuestionAnswerDescriptionImage());
                    question1.setQuestionMark(question.getQuestionMark());
                    question1.setIsNegativeAllowed(question.getIsNegativeAllowed());
                    question1.setUpdatedBy(question.getUpdatedBy());
                    return mapper.convertValue(questionRepository.save(question1), QuestionDto.class);
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
        Question question =  questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find Question")
                );
        return mapper.convertValue(question, QuestionDto.class);
    }

    public Iterable<QuestionDto> getAllQuestion() {
        Iterable<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        questions.forEach(
                question -> questionDtoList.add(mapper.convertValue(question, QuestionDto.class))
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
                question -> questionDtoList.add(mapper.convertValue(question, QuestionDto.class))
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
                question -> questionDtoList.add(mapper.convertValue(question, QuestionDto.class))
        );
        return questionDtoList;
    }
}
