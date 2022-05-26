package com.dynast.examportal.controller;

import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.model.QuestionType;
import com.dynast.examportal.service.QuestionTypeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Question Type APIs", tags = {"Question Type Controller"})
@RequestMapping(value = "/api/v1/questiontype/")
public class QuestionTypeController {
    @Autowired
    private QuestionTypeService questionTypeService;

    @ApiOperation(value = "This is used to get all Question Type", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<QuestionType> all() {
        return questionTypeService.getAll();
    }

    @ApiOperation(value = "This is used to get an Question Type", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("{questionTypeId}")
    public QuestionType getOne(@ApiParam(name = "Question Type Id", required = true)@PathVariable String questionTypeId) {
        return questionTypeService.getById(questionTypeId);
    }

    @ApiOperation(value = "This is used to create Question Type", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "Failed to create")
    })
    @PostMapping("create")
    public QuestionType create(@ApiParam(name = "Question Type", required = true) @RequestBody QuestionType questionType) {
        return questionTypeService.create(questionType);
    }

    @ApiOperation(value = "This is used to update Question Type", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "Failed to update")
    })
    @PutMapping("update")
    public QuestionType update(@ApiParam(name = "Question Type", required = true) @RequestBody QuestionType questionType) throws DataBaseException {
        return questionTypeService.update(questionType);
    }

    @ApiOperation(value = "This is used to delete Question Type", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 422, message = "Failed to delete")
    })
    @DeleteMapping("{questionTypeId}")
    public QuestionType delete(@ApiParam(name = "Question Type Id", required = true) @PathVariable String questionTypeId) throws DataBaseException {
        return questionTypeService.deleteById(questionTypeId);
    }
}
