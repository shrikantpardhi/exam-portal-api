package com.dynast.examportal.controller;

import com.dynast.examportal.model.Question;
import com.dynast.examportal.service.QuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Question APIs", tags = {"Question Controller"})
@RequestMapping(value = "/api/v1/question/")
public class QuestionController extends ApplicationController{

    @Autowired
    private QuestionService questionService;

    @PostMapping("create")
    public Question addQuestion(@RequestBody Question question) {
        question.setUpdatedBy(getUser());
        return questionService.create(question);
    }

    @PostMapping("update")
    public Question UpdateQuestion(@RequestBody Question question) {
        question.setUpdatedBy(getUser());
        return questionService.update(question);
    }

    @DeleteMapping("{questionId}")
    public Question deleteQuestion(@PathVariable String questionId) {
        return questionService.deleteById(questionId);
    }

    @GetMapping("{questionId}")
    public Question getOne(@PathVariable String questionId) {
        return questionService.findQuestionById(questionId);
    }
    @GetMapping("exam/{examId}")
    public Iterable<Question> getByExamCategoryId(@PathVariable String examId) {
        return questionService.findByExam(examId);
    }
    @GetMapping("subject/{subjectId}")
    public Iterable<Question> getBySubjectId(@PathVariable String subjectId) {
        return questionService.findBySubject(subjectId);
    }

    @GetMapping("all")
    public Iterable<Question> getAll() {
        return questionService.getAllQuestion();
    }


}
