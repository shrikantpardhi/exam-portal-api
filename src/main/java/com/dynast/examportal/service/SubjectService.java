package com.dynast.examportal.service;

import com.dynast.examportal.exception.SubjectNotFoundException;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.repository.SubjectRepository;
import com.dynast.examportal.util.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private User user;

    public Subject createNewSubject(Subject subject) {
        subject.setUpdatedBy(user.getUsername());
        return subjectRepository.save(subject);
    }

    public Iterable<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> updateSubject(@Valid Subject subject) {
        return Optional.ofNullable(subjectRepository.findById(subject.getSubjectId()).map(sub -> {
            sub.setTitle(subject.getTitle());
            sub.setDescription(subject.getDescription());
            return subjectRepository.save(sub);
        }).orElseGet(() -> {
            return subjectRepository.save(subject);
        }));
    }

    public Subject deleteSubjectById(String id) {
        Optional<Subject> sub = subjectRepository.findById(id);
        subjectRepository.delete(sub.get());
        return sub.get();
    }

    public Subject getSubjectById(String id) {
        return subjectRepository.findById(id).orElseThrow(() -> new SubjectNotFoundException("Could not find subject"));
    }

}
