package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.SubjectDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.SubjectRepository;
import com.dynast.examportal.service.SubjectService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.dynast.examportal.util.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;

    private final User user;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public SubjectServiceImpl(SubjectRepository subjectRepository, ExamRepository examRepository, User user) {
        this.subjectRepository = subjectRepository;
        this.examRepository = examRepository;
        this.user = user;
    }

    @Override
    public SubjectDto createNewSubject(SubjectDto subject) {
        subject.setUpdatedBy(user.getUsername());
        Subject s = mapper.convertValue(subject, Subject.class);
        return toSubjectDto(subjectRepository.save(s));
    }

    @Override
    public Iterable<SubjectDto> getAllSubject() {
        Iterable<Subject> subjects = subjectRepository.findAll();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        subjects.forEach(
                subject -> subjectDtos.add(toSubjectDto(subject))
        );
        return subjectDtos;
    }

    @Override
    public Optional<SubjectDto> updateSubject(SubjectDto subject) {
        return Optional.ofNullable(subjectRepository.findById(subject.getSubjectId()).map(sub -> {
            sub.setTitle(subject.getTitle());
            sub.setDescription(subject.getDescription());
            return toSubjectDto(subjectRepository.save(sub));
        }).orElseThrow(
                () -> new UnprocessableEntityException("Failed to update Subject " + subject.getTitle())
        ));
    }

    @Override
    public void deleteSubjectById(String id) {
       subjectRepository.findById(id).ifPresent(
                subjectRepository::delete
        );
    }

    @Override
    public SubjectDto getSubjectById(String id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find subject"));
        return toSubjectDto(subject);
    }

    private SubjectDto toSubjectDto(Subject subject){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setTotalExams(examRepository.countBySubject(subject));
        return subjectDto;
    }

}
