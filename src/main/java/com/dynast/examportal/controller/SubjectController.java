package com.dynast.examportal.controller;

import com.dynast.examportal.model.Subject;
import com.dynast.examportal.service.SubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(value = "Subject APIs", tags = {"Subject Controller"})
@RequestMapping(value = "/api/v1/subject/")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("{sujectId}")
//    @PreAuthorize("hasRole('Admin')")
    Subject oneSubject(@PathVariable String sujectId) {
        return subjectService.getSubjectById(sujectId);
    }

    @PostMapping({"create"})
//    @PreAuthorize("hasRole('Admin')")
    public Subject createNewSubject(@Valid @RequestBody Subject subject) {
        return subjectService.createNewSubject(subject);
    }

    @PutMapping({"update"})
//    @PreAuthorize("hasRole('Admin')")
    public Optional<Subject> updateSubject(@Valid @RequestBody Subject subject) {
        return subjectService.updateSubject(subject);
    }

    @GetMapping("all")
//    @PreAuthorize("hasRole('Admin')")
    Iterable<Subject> allSubject() {
        return subjectService.getAllSubject();
    }

    @DeleteMapping("{subjectId}")
//    @PreAuthorize("hasRole('Admin')")
    Subject deleteSubject(@PathVariable String subjectId) {
        return subjectService.deleteSubjectById(subjectId);
    }
}
