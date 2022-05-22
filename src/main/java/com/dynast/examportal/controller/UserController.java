package com.dynast.examportal.controller;

import com.dynast.examportal.model.User;
import com.dynast.examportal.service.JwtService;
import com.dynast.examportal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Api(value = "All user profile related APIs", tags = {"User Controller"})
@RequestMapping(value = "/api/v1/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

//	@PostConstruct
//    public void initRoleAndUser() {
//        userService.initRoleAndUser();
//	}

    @GetMapping("all")
    @PreAuthorize("hasRole('Admin')")
    Iterable<User> allUser() {
        return userService.getAllUser();
    }

    @PostMapping({"create"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @PutMapping({"update"})
    @PreAuthorize("hasRole('User')")
    public Optional<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @ApiOperation(value = "Get a User By Email", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping({"/user/{emailId}"})
    public User getUser(@PathVariable String emailId) {
        return userService.fetchUser(emailId);
    }


    //    Admin Operation
    @ApiOperation(value = "Get a User By UserName", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping(value = "/get/{userName}", name = "This is used to get user details by email id")
    @PreAuthorize("hasRole('Admin')")
    public User getUserDetail(@PathVariable String userName) {
        return userService.getUserDetail(userName);
    }

}
