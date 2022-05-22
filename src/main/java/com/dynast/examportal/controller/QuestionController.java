package com.dynast.examportal.controller;

import com.dynast.examportal.model.Question;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Question APIs", tags = {"Question Controller"})
@RequestMapping(value = "/api/v1/question/")
public class QuestionController {

    @PostMapping("create")
    public Question addQuestion(@RequestBody Question question) {
        return new Question();
    }

    @PostMapping("update")
    public Question UpdateQuestion(@RequestBody Question question) {
        return new Question();
    }

    @DeleteMapping("{questionId}")
    public Question deleteQuestion(@PathVariable String questionId) {
        return new Question();
    }

    @GetMapping("{questionId}")
    public Question allQuestion(@PathVariable String questionId) {
        return new Question();
    }

    @GetMapping("all")
    public Iterable<Question> oneQuestion() {
        return (Iterable<Question>) new Question();
    }


}
