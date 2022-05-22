package com.dynast.examportal.controller;

import com.dynast.examportal.model.QuestionType;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Question Type APIs", tags = {"Question Type Controller"})
@RequestMapping(value = "/api/v1/questiontype/")
public class QuestionTypeController {
    @GetMapping("all")
    public Iterable<QuestionType> all() {
        return (Iterable<QuestionType>) new QuestionType();
    }

    @GetMapping("{questionTypeId}")
    public QuestionType getOne(@PathVariable String questionTypeId) {
        return new QuestionType();
    }

    @PostMapping("create")
    public QuestionType create(@RequestBody QuestionType examCategory) {
        return new QuestionType();
    }

    @PutMapping("update")
    public QuestionType update(@RequestBody QuestionType examCategory) {
        return new QuestionType();
    }

    @DeleteMapping("{questionTypeId}")
    public QuestionType delete(@PathVariable String questionTypeId) {
        return new QuestionType();
    }
}
