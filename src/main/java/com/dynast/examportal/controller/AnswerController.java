package com.dynast.examportal.controller;

import com.dynast.examportal.model.Answer;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Answer APIs", tags = {"Answer Controller"})
@RequestMapping(value = "/api/v1/answer/")
public class AnswerController {

    @GetMapping("{questionId}/{answerId}")
    public Answer getByQuestion(@PathVariable String questionId, @PathVariable String answerId) {
        return new Answer();
    }

    @PostMapping("create")
    public Answer create(@RequestBody Answer answer) {
        return new Answer();
    }

    @PutMapping("update")
    public Answer update(@RequestBody Answer answer) {
        return new Answer();
    }

    @DeleteMapping("{answerId}")
    public Answer delete(@PathVariable String answerId) {
        return new Answer();
    }
}
