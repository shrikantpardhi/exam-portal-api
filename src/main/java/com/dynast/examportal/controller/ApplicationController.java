package com.dynast.examportal.controller;

import com.dynast.examportal.util.User;
import org.springframework.stereotype.Component;

@Component
public class ApplicationController {
    private final User user;

    public ApplicationController(User user) {
        this.user = user;
    }

    public ApplicationController() {
    }

    public String getUser() {
        return user.getUsername();
    }
}
