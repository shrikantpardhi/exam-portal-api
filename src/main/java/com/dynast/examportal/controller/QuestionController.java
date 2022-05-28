package com.dynast.examportal.controller;

import com.dynast.examportal.dto.QuestionDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.service.QuestionService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Question APIs", tags = {"Question Controller"})
@RequestMapping(value = "/api/v1/question/")
public class QuestionController extends ApplicationController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @ApiOperation(value = "This is used to create a Question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "Failed: Unable to create")
    })
    @PostMapping("create")
    public QuestionDto addQuestion(@ApiParam(name = "question", required = true) @RequestBody QuestionDto question) {
        question.setUpdatedBy(getUser());
        return questionService.create(question);
    }

    @ApiOperation(value = "This is used to update a Question")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "Failed: Unable to update")
    })
    @PutMapping("update")
    public QuestionDto UpdateQuestion(@ApiParam(name = "question", required = true) @RequestBody QuestionDto question) throws DataBaseException {
        question.setUpdatedBy(getUser());
        return questionService.update(question);
    }

    @ApiOperation(value = "This is used to delete a question by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 422, message = "Failed: unable to delete")
    })
    @DeleteMapping("{questionId}")
    public void deleteQuestion(@ApiParam(name = "questionId", required = true) @PathVariable String questionId) throws DataBaseException {
        questionService.deleteById(questionId);
    }

    @ApiOperation(value = "This is used to get a question by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("{questionId}")
    public QuestionDto getOne(@ApiParam(name = "questionId", required = true) @PathVariable String questionId) {
        return questionService.findQuestionById(questionId);
    }

    @ApiOperation(value = "This is used to get question by exam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("exam/{examId}")
    public Iterable<QuestionDto> getByExamCategoryId(@ApiParam(name = "examId", required = true) @PathVariable String examId) {
        return questionService.findByExam(examId);
    }

    @ApiOperation(value = "This is used to get question by subject")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("subject/{subjectId}")
    public Iterable<QuestionDto> getBySubjectId(@ApiParam(name = "subjectId", required = true) @PathVariable String subjectId) {
        return questionService.findBySubject(subjectId);
    }

    @ApiOperation(value = "This is used to get all questions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<QuestionDto> getAll() {
        return questionService.getAllQuestion();
    }


}
