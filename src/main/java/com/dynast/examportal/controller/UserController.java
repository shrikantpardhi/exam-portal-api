package com.dynast.examportal.controller;

import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.model.User;
import com.dynast.examportal.service.JwtService;
import com.dynast.examportal.service.UserService;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "This is used to get all users", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("all")
    @PreAuthorize("hasRole('Admin')")
    Iterable<User> allUser() {
        return userService.getAllUser();
    }

    @ApiOperation(value = "This is used to get all subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 422, message = "failed to craete")
    })
    @PostMapping({"create"})
    public User registerNewUser(@ApiParam(name = "User", required = true) @RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @ApiOperation(value = "This is used to update user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "failed to updated")
    })
    @PutMapping({"update"})
    @PreAuthorize("hasRole('User')")
    public Optional<User> updateUser(@ApiParam(name = "User", required = true) @RequestBody User user) throws DataBaseException {
        return userService.updateUser(user);
    }

    @ApiOperation(value = "Get a User By Email", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping({"/user/{emailId}"})
    public User getUser(@ApiParam(name = "Email Id", required = true)@PathVariable String emailId) {
        return userService.fetchUser(emailId);
    }

    //    Admin Operation
    @ApiOperation(value = "Get a User By UserName", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping(value = "/get/{userName}", name = "This is used to get user details by email id")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public User getUserDetail(@ApiParam(name = "Username", required = true) @PathVariable String userName) {
        return userService.getUserDetail(userName);
    }

}
