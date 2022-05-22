package com.dynast.examportal.controller;

import com.dynast.examportal.model.Exam;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam APIs", tags = {"Exam Controller"})
@RequestMapping(value = "/api/v1/exam/")
public class ExamController {

    @PostMapping("create")
    @PreAuthorize("hasRole('Admin')")
    public Exam createExam(@RequestBody Exam exam) {
        return new Exam();
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('Admin')")
    public Exam updateExam(@RequestBody Exam exam) {
        return new Exam();
    }

    @DeleteMapping("{examId}")
    @PreAuthorize("hasRole('Admin')")
    public Exam createExam(@PathVariable String examId) {
        return new Exam();
    }

    @GetMapping("all")
    public Exam getAllExam() {
        return new Exam();
    }

    @GetMapping("{examId}")
    public Exam getOneExam(@PathVariable String examId) {
        return new Exam();
    }


}
