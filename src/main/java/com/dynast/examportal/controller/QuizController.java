package com.dynast.examportal.controller;

import com.dynast.examportal.dto.QuizDto;
import com.dynast.examportal.service.QuizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "Quiz APIs", tags = {"Quiz Controller"})
@RequestMapping(value = "/api/v1/quiz/")
public class QuizController extends ApplicationController {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuizController.class);
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @ApiOperation(value = "Create a quiz.")
    @PostMapping(value = {"create"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public QuizDto createQuiz(@ApiParam(name = "quizDto", required = true) @RequestBody QuizDto quizDto) {
        return quizService.create(quizDto);
    }

    @ApiOperation(value = "update a quiz and response.")
    @PutMapping(value = {"update"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public QuizDto updateQuiz(@ApiParam(name = "quizDto", required = true) @RequestBody QuizDto quizDto) {
        return quizService.update(quizDto);
    }

    @ApiOperation(value = "Fetch all quiz")
    @GetMapping(value = {"{quizId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuizDto> getAllQuiz() {
        return quizService.fetchAll();
    }

    @ApiOperation(value = "Fetch all quiz")
    @GetMapping(value = {"educator/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuizDto>  getAllQuizByEducator(@ApiParam(name = "userId", required = true) @PathVariable String userId) {
        return quizService.fetchAllByEducator(userId);
    }

    @ApiOperation(value = "Fetch quiz by Id.")
    @GetMapping(value = {"fetch/{quizId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public QuizDto getQuizById(@ApiParam(name = "quizId", required = true) @PathVariable String quizId) {
        return quizService.fetchById(quizId);
    }

    @ApiOperation(value = "Fetch quiz by User Id.")
    @GetMapping(value = {"user/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuizDto> getQuizByUser(@ApiParam(name = "userId", required = true) @PathVariable String userId) {
        return quizService.getQuizByUser(userId);
    }

    @ApiOperation(value = "Fetch quiz by multiple exam Ids. eg.(id1, id2..)")
    @GetMapping(value = {"exams"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuizDto> getQuizByExams(@ApiParam(name = "examIds", required = true) @RequestParam String examIds) {
        return quizService.fetchAllByExams(examIds);
    }

}
