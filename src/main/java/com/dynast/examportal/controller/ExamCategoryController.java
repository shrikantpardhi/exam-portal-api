package com.dynast.examportal.controller;

import com.dynast.examportal.dto.ExamCategoryDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.service.ExamCategoryService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam Category APIs", tags = {"Exam Category Controller"})
@RequestMapping(value = "/api/v1/exam-category/")
public class ExamCategoryController extends ApplicationController {

    private final ExamCategoryService examCategoryService;

    public ExamCategoryController(ExamCategoryService examCategoryService) {
        this.examCategoryService = examCategoryService;
    }

    @ApiOperation(value = "Get list of exam category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data present")
    })
    @GetMapping("all")
    public Iterable<ExamCategoryDto> all() {
        return examCategoryService.findAll();
    }

    @ApiOperation(value = "Get exam category by exam category id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not Found - Exam Category not found")
    })
    @GetMapping("{examCategoryId}")
    public ExamCategoryDto getOne(@ApiParam(name = "examCategoryId", required = true) @PathVariable String examCategoryId) {
        return examCategoryService.findById(examCategoryId);
    }

    @ApiOperation(value = "This is used to create exam category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Created"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PostMapping("create")
    public ExamCategoryDto create(@ApiParam(name = "examCategory", required = true) @RequestBody ExamCategoryDto examCategory) {
        examCategory.setUpdatedBy(getUser());
        return examCategoryService.create(examCategory);
    }

    @ApiOperation(value = "This is used to update exam category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PutMapping("update")
    public ExamCategoryDto update(@ApiParam(name = "examCategory", required = true) @RequestBody ExamCategoryDto examCategory) throws DataBaseException {
        examCategory.setUpdatedBy(getUser());
        return examCategoryService.update(examCategory);
    }

    @ApiOperation(value = "This is used to create an answer for a question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Deleted"),
            @ApiResponse(code = 422, message = "Unable to delete")
    })
    @DeleteMapping("{examCategoryId}")
    public void delete(@ApiParam(name = "examCategoryId", required = true) @PathVariable String examCategoryId) throws DataBaseException {
        examCategoryService.delete(examCategoryId);
    }
}
