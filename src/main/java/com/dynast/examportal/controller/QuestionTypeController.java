package com.dynast.examportal.controller;

import com.dynast.examportal.model.QuestionType;
import com.dynast.examportal.service.QuestionTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Question Type APIs", tags = {"Question Type Controller"})
@RequestMapping(value = "/api/v1/questiontype/")
public class QuestionTypeController {
    @Autowired
    private QuestionTypeService  questionTypeService;

    @GetMapping("all")
    public Iterable<QuestionType> all() {
        return questionTypeService.getAll();
    }

    @GetMapping("{questionTypeId}")
    public QuestionType getOne(@PathVariable String questionTypeId) {
        return questionTypeService.getById(questionTypeId);
    }

    @PostMapping("create")
    public QuestionType create(@RequestBody QuestionType questionType) {
        return questionTypeService.create(questionType);
    }

    @PutMapping("update")
    public QuestionType update(@RequestBody QuestionType questionType) {
        return questionTypeService.update(questionType);
    }

    @DeleteMapping("{questionTypeId}")
    public QuestionType delete(@PathVariable String questionTypeId) {
        return questionTypeService.deleteById(questionTypeId);
    }
}
