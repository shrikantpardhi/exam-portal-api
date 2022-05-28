package com.dynast.examportal.service;

import com.dynast.examportal.dto.SubjectDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.repository.SubjectRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.dynast.examportal.util.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    private final User user;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public SubjectService(SubjectRepository subjectRepository, User user) {
        this.subjectRepository = subjectRepository;
        this.user = user;
    }

    public SubjectDto createNewSubject(SubjectDto subject) {
        subject.setUpdatedBy(user.getUsername());
        Subject s = mapper.convertValue(subject, Subject.class);
        return mapper.convertValue(subjectRepository.save(s), SubjectDto.class);
    }

    public Iterable<SubjectDto> getAllSubject() {
        Iterable<Subject> subjects = subjectRepository.findAll();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        subjects.forEach(
                subject -> subjectDtos.add(mapper.convertValue(subject, SubjectDto.class))
        );
        return subjectDtos;
    }

    public Optional<SubjectDto> updateSubject(SubjectDto subject) {
        return Optional.ofNullable(subjectRepository.findById(subject.getSubjectId()).map(sub -> {
            sub.setTitle(subject.getTitle());
            sub.setDescription(subject.getDescription());
            return mapper.convertValue(subjectRepository.save(sub), SubjectDto.class);
        }).orElseThrow(
                () -> new UnprocessableEntityException("Failed to update Subject " + subject.getTitle())
        ));
    }

    public void deleteSubjectById(String id) {
       subjectRepository.findById(id).ifPresent(
                subjectRepository::delete
        );
    }

    public SubjectDto getSubjectById(String id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new NotFoundException("Could not find subject"));
        return mapper.convertValue(subject, SubjectDto.class);
    }

}
