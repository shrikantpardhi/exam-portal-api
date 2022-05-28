package com.dynast.examportal.controller;

import com.dynast.examportal.dto.QuestionTypeDto;
import com.dynast.examportal.exception.DataBaseException;
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

    @ApiOperation(value = "This is used to get all Question Type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<QuestionTypeDto> all() {
        return questionTypeService.getAll();
    }

    @ApiOperation(value = "This is used to get an Question Type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("{questionTypeId}")
    public QuestionTypeDto getOne(@ApiParam(name = "questionTypeId", required = true) @PathVariable String questionTypeId) {
        return questionTypeService.getById(questionTypeId);
    }

    @ApiOperation(value = "This is used to create Question Type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "Failed to create")
    })
    @PostMapping("create")
    public QuestionTypeDto create(@ApiParam(name = "questionType", required = true) @RequestBody QuestionTypeDto questionType) {
        return questionTypeService.create(questionType);
    }

    @ApiOperation(value = "This is used to update Question Type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "Failed to update")
    })
    @PutMapping("update")
    public QuestionTypeDto update(@ApiParam(name = "questionType", required = true) @RequestBody QuestionTypeDto questionType) throws DataBaseException {
        return questionTypeService.update(questionType);
    }

    @ApiOperation(value = "This is used to delete Question Type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 422, message = "Failed to delete")
    })
    @DeleteMapping("{questionTypeId}")
    public void delete(@ApiParam(name = "questionTypeId", required = true) @PathVariable String questionTypeId) throws DataBaseException {
        questionTypeService.deleteById(questionTypeId);
    }
}
