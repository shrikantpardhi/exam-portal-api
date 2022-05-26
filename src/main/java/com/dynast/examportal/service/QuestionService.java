package com.dynast.examportal.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Question create(Question question) {
        Optional<Subject> subject = subjectRepository.findById(question.getSubject().getSubjectId());
        Optional<Exam> exam = examRepository.findById(question.getExam().getExamId());
        Optional<QuestionType> questionType = questionTypeRepository.findById(question.getQuestionType().getQuestionTypeId());
        question.setSubject(subject.get());
        question.setExam(exam.get());
        question.setQuestionType(questionType.get());
        return questionRepository.save(question);
    }

    public Question update(Question question) {
        Optional<Subject> subject = subjectRepository.findById(question.getSubject().getSubjectId());
        Optional<Exam> exam = examRepository.findById(question.getExam().getExamId());
        Optional<QuestionType> questionType = questionTypeRepository.findById(question.getQuestionType().getQuestionTypeId());
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
                    return questionRepository.save(question1);
                }
        ).orElseThrow(() -> new UnprocessableEntityException("Could not able to process request!"));
    }

    public Question deleteById(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find Question!")
                );
        questionRepository.delete(question);
        return question;
    }

    public Question findQuestionById(String questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find Question")
                );
    }

    public Iterable<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    public Iterable<Question> findByExam(String examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(
                () -> new NotFoundException("Could not find exam!")
        );
        return questionRepository.findByExam(exam);
    }

    public Iterable<Question> findBySubject(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Could not find subject!")
        );
        return questionRepository.findBySubject(subject);
    }
}
