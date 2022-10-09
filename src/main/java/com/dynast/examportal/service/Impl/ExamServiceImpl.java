package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.ExamCategoryDto;
import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.dto.SubjectDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.repository.ExamCategoryRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.SubjectRepository;
import com.dynast.examportal.service.ExamService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    private final SubjectRepository subjectRepository;

    private final ExamCategoryRepository examCategoryRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public ExamServiceImpl(ExamRepository examRepository, SubjectRepository subjectRepository, ExamCategoryRepository examCategoryRepository) {
        this.examRepository = examRepository;
        this.subjectRepository = subjectRepository;
        this.examCategoryRepository = examCategoryRepository;
    }

    public ExamDto create(ExamDto examDto) {
        return saveExamAndReturnDto(examDto, new Exam());
    }


    public ExamDto update(ExamDto examDto) {
        return examRepository.findById(examDto.getExamId()).map(
                exam -> saveExamAndReturnDto(examDto, exam)
        ).orElseThrow(
                () -> new UnprocessableEntityException("Unable to update Exam" + examDto.getExamTitle())
        );
    }

    public void delete(String examId) {
        examRepository.findById(examId).ifPresent(
                examRepository::delete
        );
    }

    public Iterable<ExamDto> getAll() {
        Iterable<Exam> exams = examRepository.findAll();
        List<ExamDto> examDtoList = new ArrayList<>();
        exams.forEach(
                exam -> examDtoList.add(getExamDto(exam))
        );
        return examDtoList;
    }

    public ExamDto getOne(String examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new NotFoundException("Could not find Exam!"));
        return getExamDto(exam);
    }

    private ExamDto getExamDto(Exam exam) {
        Subject subject = subjectRepository.findById(exam.getSubject().getSubjectId()).orElseThrow(
                () -> new NotFoundException("Could  not find subject!")
        );
        ExamCategory examCategory = examCategoryRepository.findById(exam.getExamCategory().getExamCategoryId()).orElseThrow(
                () -> new NotFoundException("Could  not find exam category!")
        );
        ExamDto examDto = mapper.convertValue(examRepository.save(exam), ExamDto.class);
        examDto.setSubjectDto(mapper.convertValue(subject, SubjectDto.class));
        examDto.setExamCategoryDto(mapper.convertValue(examCategory, ExamCategoryDto.class));
        return examDto;
    }

    private ExamDto saveExamAndReturnDto(ExamDto examDto, Exam exam) {
        Subject subject = subjectRepository.findById(examDto.getSubjectDto().getSubjectId()).orElseThrow(
                () -> new NotFoundException("Could  not find subject!")
        );
        ExamCategory examCategory = examCategoryRepository.findById(examDto.getExamCategoryDto().getExamCategoryId()).orElseThrow(
                () -> new NotFoundException("Could  not find exam category!")
        );
        exam.setExamTitle(exam.getExamTitle());
        exam.setExamDescription(exam.getExamDescription());
        exam.setSubject(subject);
        exam.setExamCategory(examCategory);
        exam.setTotalMark(exam.getTotalMark());
        exam.setExamEndDate(exam.getExamEndDate());
        exam.setExamDuration(exam.getExamDuration());
        exam.setIsNegativeAllowed(exam.getIsNegativeAllowed());
        exam.setIsPaid(exam.getIsPaid());
        exam.setUpdatedBy(exam.getUpdatedBy());
        ExamDto examDto1 = mapper.convertValue(examRepository.save(exam), ExamDto.class);
        examDto1.setSubjectDto(mapper.convertValue(subject, SubjectDto.class));
        examDto.setExamCategoryDto(mapper.convertValue(examCategory, ExamCategoryDto.class));
        return examDto1;
    }
}
