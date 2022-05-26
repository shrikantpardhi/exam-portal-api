package com.dynast.examportal.service;

import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.repository.ExamCategoryRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamCategoryRepository examCategoryRepository;

    public Exam create(Exam exam) {
        Optional<Subject> subject = subjectRepository.findById(exam.getSubject().getSubjectId());
        ExamCategory examCategory   = examCategoryRepository.findById(exam.getExamCategory().getExamCategoryId()).orElseThrow(
                () -> new NotFoundException("Could  not find exam category!")
        );
        exam.setSubject(subject.get());
        exam.setExamCategory(examCategory);
        return examRepository.save(exam);
    }

    public Exam update(Exam exam) {
        Optional<Subject> subject = subjectRepository.findById(exam.getSubject().getSubjectId());
        ExamCategory examCategory   = examCategoryRepository.findById(exam.getExamCategory().getExamCategoryId()).orElseThrow(
                () -> new NotFoundException("Could  not find exam category!")
        );
        return examRepository.findById(exam.getExamId()).map(
                exam1 -> {
                    exam1.setExamTitle(exam.getExamTitle());
                    exam1.setExamDescription(exam.getExamDescription());
                    exam1.setSubject(subject.get());
                    exam1.setExamCategory(examCategory);
                    exam1.setTotalMark(exam.getTotalMark());
                    exam1.setExamEndDate(exam.getExamEndDate());
                    exam1.setExamDuration(exam.getExamDuration());
                    exam1.setIsNegativeAllowed(exam.getIsNegativeAllowed());
                    exam1.setIsPaid(exam.getIsPaid());
                    exam1.setUpdatedBy(exam.getUpdatedBy());
                    return examRepository.save(exam1);
                }
        ).orElseThrow(
                () -> new UnprocessableEntityException("Unable to update Exam" + exam.getExamTitle())
        );
    }

    public Exam delete(String examId) {
        Optional<Exam> exam = examRepository.findById(examId);
        examRepository.delete(exam.get());
        return exam.get();
    }

    public Iterable<Exam> getAll() {
        return examRepository.findAll();
    }

    public Exam getOne(String examId) {
        return examRepository.findById(examId).orElseThrow(()-> new NotFoundException("Could not find Exam!"));
    }
}
