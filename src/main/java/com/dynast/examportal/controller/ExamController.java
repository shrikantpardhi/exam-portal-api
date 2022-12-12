package com.dynast.examportal.controller;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.service.ExamService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exam APIs", tags = {"Exam Controller"})
@RequestMapping(value = "/api/v1/exam/")
public class ExamController extends ApplicationController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @ApiOperation(value = "Create an exam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PostMapping(value = {"create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto createExam(@ApiParam(name = "exam", required = true) @RequestBody ExamDto exam) {
        exam.setUserDto(getUserDto());
        return examService.create(exam);
    }

    @ApiOperation(value = "Update an exam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PutMapping(value = {"update"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto updateExam(@ApiParam(name = "exam", required = true) @RequestBody ExamDto exam) {
        exam.setUserDto(getUserDto());
        return examService.update(exam);
    }

    @ApiOperation(value = "Delete an exam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Deleted"),
            @ApiResponse(code = 422, message = "Unable to delete")
    })
    @DeleteMapping(value = {"{examId}"})
    public void deleteExam(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        examService.delete(examId);
    }

    @ApiOperation(value = "Get list of Exams")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping(value = {"all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ExamDto> getAllExam() {
        return examService.getAll();
    }

    @ApiOperation(value = "Get an Exam by exam id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping(value = {"{examId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto getOneExam(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        return examService.findByExamId(examId);
    }

    @ApiOperation(value = "Change an Exam active status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status Updated"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @PostMapping(value = {"change-status"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto changeStatus(@ApiParam(name = "exam", required = true) @RequestBody ExamDto exam) {
        exam.setUserDto(getUserDto());
        return examService.changeStatus(exam);
    }
}
