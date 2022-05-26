package com.dynast.examportal.controller;

import com.dynast.examportal.util.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationController {
    @Autowired
    private User user;
    public String getUser(){
        return user.getUsername();
    }
}
