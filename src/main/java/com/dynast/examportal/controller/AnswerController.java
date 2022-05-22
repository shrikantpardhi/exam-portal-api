package com.dynast.examportal.controller;

import com.dynast.examportal.model.Answer;
import com.dynast.examportal.service.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Answer APIs", tags = {"Answer Controller"})
@RequestMapping(value = "/api/v1/answer/")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @ApiOperation(value = "Get Answers for Question Id", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data fetched successful"),
            @ApiResponse(code = 422, message = "Not found - The answers was not found for the question")
    })
    @GetMapping("{questionId}")
    public Iterable<Answer> getByQuestion(@PathVariable String questionId) {
        return answerService.getAllByQuestion(questionId);
    }

    @ApiOperation(value = "Get Answer by answer Id", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data fetched successful"),
            @ApiResponse(code = 422, message = "Not found - The answer was not found")
    })
    @GetMapping("{answerId}")
    public Answer getByAnswer(@PathVariable String answerId) {
        return answerService.getByAnswerId(answerId);
    }

    @ApiOperation(value = "This is used to create an answer for a question", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Created"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PostMapping("create")
    public Answer create(@RequestBody Answer answer) {
        return answerService.create(answer);
    }

    @ApiOperation(value = "This is used to update an answer", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated"),
            @ApiResponse(code = 422, message = "Unable to process request")
    })
    @PutMapping("update")
    public Answer update(@RequestBody Answer answer) {
        return answerService.update(answer);
    }

    @ApiOperation(value = "This is used to delete answer by id", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found - The answer was not found")
    })
    @DeleteMapping("{answerId}")
    public void delete( @PathVariable String answerId) {
        answerService.delete(answerId);
    }
}
