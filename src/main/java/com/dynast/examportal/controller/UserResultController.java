package com.dynast.examportal.controller;

import com.dynast.examportal.dto.UserResultDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.service.UserResultService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "User Result APIs", tags = {"User Result Controller"})
@RequestMapping(value = "/api/v1/result/")
public class UserResultController extends ApplicationController {

    @Autowired
    private UserResultService userResultService;

    @ApiOperation(value = "This is used to get all results")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("all")
    public Iterable<UserResultDto> all() {
        return userResultService.getAll();
    }

    @ApiOperation(value = "This is used to get all result by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("{userName}")
    public Iterable<UserResultDto> getAllResultByUser(@ApiParam(name = "userName", required = true) @PathVariable String userName) {
        return userResultService.getAllResultByUser(userName);
    }

    @ApiOperation(value = "This is used to get Result by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("{userName}/{resultId}")
    public UserResultDto getOne(@ApiParam(name = "userName", required = true) @PathVariable String userName, @ApiParam(name = "resultId", required = true) @PathVariable String resultId) {
        return userResultService.getResultByUser(userName, resultId);
    }

    @ApiOperation(value = "This is used to create a User Result")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PostMapping("create")
    public UserResultDto create(@ApiParam(name = "userResult", required = true) @RequestBody UserResultDto userResult) {
        return userResultService.create(userResult);
    }

    @ApiOperation(value = "This is used to update a User Result")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "failed to update")
    })
    @PutMapping("update")
    public UserResultDto update(@ApiParam(name = "userResult", required = true) @RequestBody UserResultDto userResult) throws DataBaseException {
        return userResultService.update(userResult);
    }

}
