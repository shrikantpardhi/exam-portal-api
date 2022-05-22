package com.dynast.examportal.controller;

import com.dynast.examportal.model.ExamCategory;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam Category APIs", tags = {"Exam Category Controller"})
@RequestMapping(value = "/api/v1/examcategory/")
public class ExamCategoryController {

    @GetMapping("all")
    public Iterable<ExamCategory> all() {
        return (Iterable<ExamCategory>) new ExamCategory();
    }

    @GetMapping("{examCategoryId}")
    public ExamCategory getOne(@PathVariable String examCategoryId) {
        return new ExamCategory();
    }

    @PostMapping("create")
    public ExamCategory create(@RequestBody ExamCategory examCategory) {
        return new ExamCategory();
    }

    @PutMapping("update")
    public ExamCategory update(@RequestBody ExamCategory examCategory) {
        return new ExamCategory();
    }

    @DeleteMapping("{examCategoryId}")
    public ExamCategory delete(@PathVariable String examCategoryId) {
        return new ExamCategory();
    }
}
