package com.dynast.examportal.controller;

import com.dynast.examportal.model.Exam;
import com.dynast.examportal.service.ExamService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam APIs", tags = {"Exam Controller"})
@RequestMapping(value = "/api/v1/exam/")
public class ExamController extends ApplicationController {

    @Autowired
    private ExamService examService;

    @PostMapping("create")
//    @PreAuthorize("hasRole('Admin')")
    public Exam createExam(@RequestBody Exam exam) {
        exam.setUpdatedBy(getUser());
        return examService.create(exam);
    }

    @PutMapping("update")
//    @PreAuthorize("hasRole('Admin')")
    public Exam updateExam(@RequestBody Exam exam) {
        exam.setUpdatedBy(getUser());
        return examService.update(exam);
    }

    @DeleteMapping("{examId}")
//    @PreAuthorize("hasRole('Admin')")
    public Exam deleteExam(@PathVariable String examId) {
        return examService.delete(examId);
    }

    @GetMapping("all")
    public Iterable<Exam> getAllExam() {
        return examService.getAll();
    }

    @GetMapping("{examId}")
    public Exam getOneExam(@PathVariable String examId) {
        return examService.getOne(examId);
    }


}
