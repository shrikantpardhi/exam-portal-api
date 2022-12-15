package com.dynast.examportal.controller;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.service.ExamService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Exam APIs", tags = {"Exam Controller"})
@RequestMapping(value = "/api/v1/exam/")
public class ExamController extends ApplicationController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

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
        LOGGER.info("in create exam {}", exam.getExamTitle());
        exam.setUser(getUserDto());
        return examService.create(exam);
    }

    @ApiOperation(value = "Update an exam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PutMapping(value = {"update"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto updateExam(@ApiParam(name = "exam", required = true) @RequestBody ExamDto exam) {
        LOGGER.info("in update exam {}", exam.getExamId());
        exam.setUser(getUserDto());
        return examService.update(exam);
    }

    @ApiOperation(value = "Delete an exam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Deleted"),
            @ApiResponse(code = 422, message = "Unable to delete")
    })
    @DeleteMapping(value = {"{examId}"})
    public void deleteExam(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        LOGGER.info("in delete exam {}", examId);
        examService.delete(examId);
    }

    @ApiOperation(value = "Get list of Exams")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping(value = {"all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ExamDto> getAllExam() {
        LOGGER.info("in get all exam.");
        return examService.getAll();
    }

    @ApiOperation(value = "Get an Exam by exam id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping(value = {"{examId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto getOneExam(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        LOGGER.info("in get one exam {}", examId);
        return examService.getByExamId(examId);
    }

    @ApiOperation(value = "Change an Exam active status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status Updated"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @PostMapping(value = {"{examId}/change-status"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto changeStatus(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        LOGGER.info("in change exam status {}", examId);
        return examService.changeStatus(examId);
    }

    @PostMapping(value = {"code/{code}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExamDto> getByEducatorCode(@ApiParam(name = "code", required = true) @PathVariable String code) {
        LOGGER.info("in get exam by educator code {}", code);
        return examService.getByEducatorCode(code);
    }

    @PostMapping(value = {"user/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExamDto> getByUser(@ApiParam(name = "userId", required = true) @PathVariable String userId) {
        LOGGER.info("in get exam by userId {}", userId);
        return examService.getByUser(userId);
    }
}
