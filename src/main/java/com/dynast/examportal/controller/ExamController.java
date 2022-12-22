package com.dynast.examportal.controller;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.service.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
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
    @PostMapping(value = {"create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto createExam(@ApiParam(name = "exam", required = true) @RequestBody ExamDto exam) {
        LOGGER.info("in create exam {}", exam.getExamTitle());
        exam.setUser(getUserDto());
        return examService.create(exam);
    }

    @ApiOperation(value = "Update an exam")
    @PutMapping(value = {"update"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto updateExam(@ApiParam(name = "exam", required = true) @RequestBody ExamDto exam) {
        LOGGER.info("in update exam {}", exam.getExamId());
        exam.setUser(getUserDto());
        return examService.update(exam);
    }

    @ApiOperation(value = "Delete an exam")
    @DeleteMapping(value = {"{examId}"})
    public void deleteExam(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        LOGGER.info("in delete exam {}", examId);
        examService.delete(examId);
    }

    @ApiOperation(value = "Get list of Exams")
    @GetMapping(value = {"all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ExamDto> getAllExam() {
        LOGGER.info("in get all exam.");
        return examService.getAll();
    }

    @ApiOperation(value = "Get an Exam by exam id")
    @GetMapping(value = {"{examId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto getOneExam(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        LOGGER.info("in get one exam {}", examId);
        return examService.getByExamId(examId);
    }

    @ApiOperation(value = "Change an Exam status")
    @PutMapping(value = {"{examId}/change-status"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDto changeStatus(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        LOGGER.info("in change exam status {}", examId);
        return examService.changeStatus(examId);
    }

    @ApiOperation(value = "Get exams by educator code.")
    @GetMapping(value = {"code/{code}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExamDto> getByEducatorCode(@ApiParam(name = "code", required = true) @PathVariable String code) {
        LOGGER.info("in get exam by educator code {}", code);
        return examService.getByEducatorCode(code);
    }

    @ApiOperation(value = "Get exams by educator codes.")
    @GetMapping(value = {"codes"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExamDto> getByEducatorCodes(@ApiParam(name = "educatorCodes", required = true) @RequestParam List<String> educatorCodes) {
        LOGGER.info("in get exam by list of educator code {}", educatorCodes);
        return examService.getByEducatorCodes(new HashSet<>(educatorCodes));
    }

    @ApiOperation(value = "Get exams by user id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 404, message = "No user found.")
    })
    @GetMapping(value = {"user/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExamDto> getByUser(@ApiParam(name = "userId", required = true) @PathVariable String userId) {
        LOGGER.info("in get exam by userId {}", userId);
        return examService.getByUser(userId);
    }

    @ApiOperation(value = "Get exams by user id and educator codes in user.")
    @GetMapping(value = {"user/{userId}/codes"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExamDto> getByUserEducatorCodes(@ApiParam(name = "userId", required = true) @PathVariable String userId) {
        LOGGER.info("in get exam by getByUserEducatorCodes {}", userId);
        return examService.getByUserEducatorCodes(userId);
    }

    @ApiOperation(value = "Get exams by tag.")
    @GetMapping(value = {"tag/{name}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExamDto> getByTag(@ApiParam(name = "name", required = true) @PathVariable String name) {
        LOGGER.info("in get exam by getByTag {}", name);
        return examService.getByTag(name);
    }

}
