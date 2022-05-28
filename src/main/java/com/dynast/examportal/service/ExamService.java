package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.repository.ExamCategoryRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.SubjectRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    private final SubjectRepository subjectRepository;

    private final ExamCategoryRepository examCategoryRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ExamService(ExamRepository examRepository, SubjectRepository subjectRepository, ExamCategoryRepository examCategoryRepository) {
        this.examRepository = examRepository;
        this.subjectRepository = subjectRepository;
        this.examCategoryRepository = examCategoryRepository;
    }

    public ExamDto create(ExamDto exam) {
        Optional<Subject> subject = subjectRepository.findById(exam.getSubjectDto().getSubjectId());
        ExamCategory examCategory = examCategoryRepository.findById(exam.getExamCategoryDto().getExamCategoryId()).orElseThrow(
                () -> new NotFoundException("Could  not find exam category!")
        );
        Exam e = mapper.convertValue(exam, Exam.class);
        e.setSubject(subject.orElse(null));
        e.setExamCategory(examCategory);
        return mapper.convertValue(examRepository.save(e), ExamDto.class);
    }

    public ExamDto update(ExamDto exam) {
        Optional<Subject> subject = subjectRepository.findById(exam.getSubjectDto().getSubjectId());
        ExamCategory examCategory = examCategoryRepository.findById(exam.getExamCategoryDto().getExamCategoryId()).orElseThrow(
                () -> new NotFoundException("Could  not find exam category!")
        );
        return examRepository.findById(exam.getExamId()).map(
                exam1 -> {
                    exam1.setExamTitle(exam.getExamTitle());
                    exam1.setExamDescription(exam.getExamDescription());
                    exam1.setSubject(subject.orElse(null));
                    exam1.setExamCategory(examCategory);
                    exam1.setTotalMark(exam.getTotalMark());
                    exam1.setExamEndDate(exam.getExamEndDate());
                    exam1.setExamDuration(exam.getExamDuration());
                    exam1.setIsNegativeAllowed(exam.getIsNegativeAllowed());
                    exam1.setIsPaid(exam.getIsPaid());
                    exam1.setUpdatedBy(exam.getUpdatedBy());
                    return mapper.convertValue(examRepository.save(exam1), ExamDto.class);
                }
        ).orElseThrow(
                () -> new UnprocessableEntityException("Unable to update Exam" + exam.getExamTitle())
        );
    }

    public void delete(String examId) {
        Optional<Exam> exam = examRepository.findById(examId);
        assert exam.orElse(null) != null;
        examRepository.delete(exam.orElse(null));
    }

    public Iterable<ExamDto> getAll() {
        Iterable<Exam> exams = examRepository.findAll();
        List<ExamDto> examDtoList = new ArrayList<>();
        exams.forEach(
                exam -> examDtoList.add(mapper.convertValue(exam, ExamDto.class))
        );
        return examDtoList;
    }

    public ExamDto getOne(String examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new NotFoundException("Could not find Exam!"));
        return mapper.convertValue(exam, ExamDto.class);
    }
}
