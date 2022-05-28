package com.dynast.examportal.controller;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.service.ExamService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam APIs", tags = {"Exam Controller"})
@RequestMapping(value = "/api/v1/exam/")
public class ExamController extends ApplicationController {

    @Autowired
    private ExamService examService;

    @ApiOperation(value = "This is used to create an exam", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PostMapping("create")
//    @PreAuthorize("hasRole('Admin')")
    public ExamDto createExam(@ApiParam(name = "exam", required = true) @RequestBody ExamDto exam) {
        exam.setUpdatedBy(getUser());
        return examService.create(exam);
    }

    @ApiOperation(value = "This is used to upate an exam", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PutMapping("update")
//    @PreAuthorize("hasRole('Admin')")
    public ExamDto updateExam(@ApiParam(name = "exam", required = true)@RequestBody ExamDto exam) {
        exam.setUpdatedBy(getUser());
        return examService.update(exam);
    }

    @ApiOperation(value = "This is used to delete an exam", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Deleted"),
            @ApiResponse(code = 422, message = "Unable to delete")
    })
    @DeleteMapping("{examId}")
//    @PreAuthorize("hasRole('Admin')")
    public void deleteExam(@ApiParam(name = "examId", required = true)@PathVariable String examId) {
        examService.delete(examId);
    }

    @ApiOperation(value = "This is used to get list of Exmas", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<ExamDto> getAllExam() {
        return examService.getAll();
    }

    @ApiOperation(value = "This is used to get an Exam by exam id", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("{examId}")
    public ExamDto getOneExam(@ApiParam(name = "examId", required = true)@PathVariable String examId) {
        return examService.getOne(examId);
    }


}
