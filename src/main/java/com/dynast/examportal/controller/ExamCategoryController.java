package com.dynast.examportal.controller;

import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.model.ExamCategory;
import com.dynast.examportal.service.ExamCategoryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam Category APIs", tags = {"Exam Category Controller"})
@RequestMapping(value = "/api/v1/examcategory/")
public class ExamCategoryController extends ApplicationController {

    @Autowired
    private ExamCategoryService examCategoryService;

    @ApiOperation(value = "Get list of exam category", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data present")
    })
    @GetMapping("all")
    public Iterable<ExamCategory> all() {
        return examCategoryService.findAll();
    }

    @ApiOperation(value = "Get exam category by exam category id", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not Found - Exam Category not found")
    })
    @GetMapping("{examCategoryId}")
    public ExamCategory getOne(@ApiParam(name = "Exam Category Id", required = true)@PathVariable String examCategoryId) {
        return examCategoryService.findById(examCategoryId);
    }

    @ApiOperation(value = "This is used to create exam category", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Created"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PostMapping("create")
    public ExamCategory create(@ApiParam(name = "Exam Category", required = true)@RequestBody ExamCategory examCategory) {
        examCategory.setUpdatedBy(getUser());
        return examCategoryService.create(examCategory);
    }

    @ApiOperation(value = "This is used to update exam category", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PutMapping("update")
    public ExamCategory update(@ApiParam(name = "Exam Category", required = true)@RequestBody ExamCategory examCategory) throws DataBaseException {
        examCategory.setUpdatedBy(getUser());
        return examCategoryService.update(examCategory);
    }

    @ApiOperation(value = "This is used to create an answer for a question", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Deleted"),
            @ApiResponse(code = 422, message = "Unable to delete")
    })
    @DeleteMapping("{examCategoryId}")
    public ExamCategory delete(@ApiParam(name = "Exam Category id", required = true)@PathVariable String examCategoryId) throws DataBaseException {
        return examCategoryService.delete(examCategoryId);
    }
}
