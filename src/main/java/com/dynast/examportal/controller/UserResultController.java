package com.dynast.examportal.controller;

import com.dynast.examportal.model.UserResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "User Result APIs", tags = {"User Result Controller"})
@RequestMapping(value = "/api/v1/result/")
public class UserResultController {

    @GetMapping("all")
    public Iterable<UserResult> all() {
        return (Iterable<UserResult>) new UserResult();
    }

    @GetMapping("{userName}")
    public Iterable<UserResult> getByUser(@PathVariable String userName) {
        return (Iterable<UserResult>) new UserResult();
    }

    @GetMapping("{userName}/{resultId}")
    public Iterable<UserResult> getOne(@PathVariable String userName, @PathVariable String resultId) {
        return (Iterable<UserResult>) new UserResult();
    }

    @PostMapping("create")
    public UserResult create(@RequestBody UserResult userResult) {
        return new UserResult();
    }

}
