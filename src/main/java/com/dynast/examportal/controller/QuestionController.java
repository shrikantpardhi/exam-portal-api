package com.dynast.examportal.controller;

import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.service.QuestionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Question APIs", tags = {"Question Controller"})
@RequestMapping(value = "/api/v1/question/")
public class QuestionController extends ApplicationController {

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "This is used to create a Question", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "Failed: Unable to create")
    })
    @PostMapping("create")
    public Question addQuestion(@ApiParam(name = "Question", required = true)@RequestBody Question question) {
        question.setUpdatedBy(getUser());
        return questionService.create(question);
    }

    @ApiOperation(value = "This is used to update a Question", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "Failed: Unable to update")
    })
    @PutMapping("update")
    public Question UpdateQuestion(@ApiParam(name = "Question", required = true)@RequestBody Question question) throws DataBaseException {
        question.setUpdatedBy(getUser());
        return questionService.update(question);
    }

    @ApiOperation(value = "This is used to delete a question by id", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 422, message = "Failed: unable to delete")
    })
    @DeleteMapping("{questionId}")
    public Question deleteQuestion(@ApiParam(name = "Question Id", required = true)@PathVariable String questionId) throws DataBaseException {
        return questionService.deleteById(questionId);
    }

    @ApiOperation(value = "This is used to get a question by id", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("{questionId}")
    public Question getOne(@ApiParam(name = "Question Id", required = true) @PathVariable String questionId) {
        return questionService.findQuestionById(questionId);
    }

    @ApiOperation(value = "This is used to get question by exam", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("exam/{examId}")
    public Iterable<Question> getByExamCategoryId(@ApiParam(name = "Exam Id", required = true)@PathVariable String examId) {
        return questionService.findByExam(examId);
    }

    @ApiOperation(value = "This is used to get question by subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("subject/{subjectId}")
    public Iterable<Question> getBySubjectId(@ApiParam(name = "Subject Id", required = true) @PathVariable String subjectId) {
        return questionService.findBySubject(subjectId);
    }

    @ApiOperation(value = "This is used to get all questions", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<Question> getAll() {
        return questionService.getAllQuestion();
    }


}
