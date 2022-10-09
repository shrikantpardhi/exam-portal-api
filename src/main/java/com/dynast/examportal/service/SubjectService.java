package com.dynast.examportal.service;

import com.dynast.examportal.dto.SubjectDto;

import java.util.Optional;

public interface SubjectService {
    SubjectDto getSubjectById(String sujectId);

    SubjectDto createNewSubject(SubjectDto subject);

    Optional<SubjectDto> updateSubject(SubjectDto subject);

    Iterable<SubjectDto> getAllSubject();

    void deleteSubjectById(String subjectId);
}
