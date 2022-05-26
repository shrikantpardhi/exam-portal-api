package com.dynast.examportal.controller;

import com.dynast.examportal.model.UserResult;
import com.dynast.examportal.service.UserResultService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "User Result APIs", tags = {"User Result Controller"})
@RequestMapping(value = "/api/v1/result/")
public class UserResultController extends ApplicationController{

    @Autowired
    private UserResultService userResultService;

    @GetMapping("all")
    public Iterable<UserResult> all() {
        return userResultService.getAll();
    }

    @GetMapping("{userName}")
    public Iterable<UserResult> getByUser(@PathVariable String userName) {
        return userResultService.getAllResultByUser(userName);
    }

    @GetMapping("{userName}/{resultId}")
    public UserResult getOne(@PathVariable String userName, @PathVariable String resultId) {
        return userResultService.getResultByUser(userName, resultId);
    }

    @PostMapping("create")
    public UserResult create(@RequestBody UserResult userResult) {
        return userResultService.create(userResult);
    }

    @PostMapping("update")
    public UserResult update(@RequestBody UserResult userResult) {
        return userResultService.update(userResult);
    }

}
