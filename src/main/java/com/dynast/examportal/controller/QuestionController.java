package com.dynast.examportal.controller;

import com.dynast.examportal.dto.QuestionDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.service.QuestionService;
import com.dynast.examportal.util.QuestionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;

@RestController
@Api(value = "Question APIs", tags = {"Question Controller"})
@RequestMapping(value = "/api/v1/question/")
public class QuestionController extends ApplicationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @ApiOperation(value = "Create a Question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "Failed: Unable to create")
    })
    @PostMapping("create")
    public QuestionDto addQuestion(@ApiParam(name = "question", required = true) @RequestBody QuestionDto question) {
        LOGGER.info("in create question");
        return questionService.create(question);
    }

    @ApiOperation(value = "This is used to update a Question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "Failed: Unable to update")
    })
    @PutMapping("update")
    public QuestionDto UpdateQuestion(@ApiParam(name = "question", required = true) @RequestBody QuestionDto question) throws DataBaseException {
        LOGGER.info("in update question");
        return questionService.update(question);
    }

    @ApiOperation(value = "This is used to delete a question by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 422, message = "Failed: unable to delete")
    })
    @DeleteMapping("{questionId}")
    public void deleteQuestion(@ApiParam(name = "questionId", required = true) @PathVariable String questionId) throws DataBaseException {
        LOGGER.info("in delete question");
        questionService.deleteById(questionId);
    }

    @ApiOperation(value = "This is used to get a question by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("{questionId}")
    public QuestionDto getOne(@ApiParam(name = "questionId", required = true) @PathVariable String questionId) {
        LOGGER.info("in get question by id");
        return questionService.findQuestionById(questionId);
    }

    @ApiOperation(value = "This is used to get question by exam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("exam/{examId}")
    public Iterable<QuestionDto> getByExamCategoryId(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        LOGGER.info("in get question by exam category id");
        return questionService.findByExam(examId);
    }

    @ApiOperation(value = "This is used to get all questions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<QuestionDto> getAll() {
        LOGGER.info("in get all questions");
        return questionService.getAllQuestion();
    }

    @ApiOperation(value = "Get all Question Types")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("get/types")
    public EnumSet<QuestionType> getQuestionTypes() {
        LOGGER.info("Get all Question Types");
        return questionService.getQuestionTypes();
    }
}
