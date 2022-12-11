package com.dynast.examportal.controller;

import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.service.UserService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.dynast.examportal.util.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationController {
    @Autowired
    private User user;

    @Autowired
    private UserService userService;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public UserDto getUserDto() {
        return userService.getUserByEmailId(user.getEmail());
    }

    public com.dynast.examportal.model.User getUser() {
        return mapper.convertValue(userService.getUserByEmailId(user.getEmail()), com.dynast.examportal.model.User.class);
    }
}
