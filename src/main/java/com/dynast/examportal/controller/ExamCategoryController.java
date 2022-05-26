package com.dynast.examportal.controller;

import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.service.ExamCategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam Category APIs", tags = {"Exam Category Controller"})
@RequestMapping(value = "/api/v1/examcategory/")
public class ExamCategoryController extends ApplicationController {

    @Autowired
    private ExamCategoryService examCategoryService;

    @GetMapping("all")
    public Iterable<ExamCategory> all() {
        return examCategoryService.findAll();
    }

    @GetMapping("{examCategoryId}")
    public ExamCategory getOne(@PathVariable String examCategoryId) {
        return examCategoryService.findById(examCategoryId);
    }

    @PostMapping("create")
    public ExamCategory create(@RequestBody ExamCategory examCategory) {
        examCategory.setUpdatedBy(getUser());
        return examCategoryService.create(examCategory);
    }

    @PutMapping("update")
    public ExamCategory update(@RequestBody ExamCategory examCategory) {
        examCategory.setUpdatedBy(getUser());
        return examCategoryService.update(examCategory);
    }

    @DeleteMapping("{examCategoryId}")
    public ExamCategory delete(@PathVariable String examCategoryId) {
        return examCategoryService.delete(examCategoryId);
    }
}
