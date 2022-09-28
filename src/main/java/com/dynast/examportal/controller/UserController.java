package com.dynast.examportal.controller;

import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.JwtRequest;
import com.dynast.examportal.model.JwtResponse;
import com.dynast.examportal.service.JwtService;
import com.dynast.examportal.service.UserService;
import io.swagger.annotations.*;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "All user profile related APIs", tags = {"User Controller"})
@RequestMapping(value = "/api/v1/user/")
public class UserController {
    private final UserService userService;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

//    @PostConstruct
//    public void initRoleAndUser() {
//        userService.initRoleAndUser();
//    }

    @ApiOperation(value = "This is used to get all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("all")
    @PreAuthorize("hasRole('Admin')")
    Iterable<UserDto> allUser() {
        return userService.getAllUser();
    }

    @ApiOperation(value = "This is used to get all subject")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PostMapping(value = {"create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponse registerNewUser(@ApiParam(name = "user", required = true) @RequestBody UserDto user) throws Exception {
        UserDto u = userService.registerNewUser(user);
        if (u != null) {
            JwtRequest jwtRequest = new JwtRequest(user.getEmail(), user.getUserPassword());
            return jwtService.createJwtToken(jwtRequest);
        } else {
            throw new UnprocessableEntityException("Unable to create New User");
        }

    }

    @ApiOperation(value = "This is used to update user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "failed to updated")
    })
    @PutMapping(value = {"update"})
    @PreAuthorize("hasRole('User')")
    public UserDto updateUser(@ApiParam(name = "user", required = true) @RequestBody UserDto user) throws DataBaseException {
        return userService.updateUser(user);
    }

    @ApiOperation(value = "Get a User By Email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping({"/user/{emailId}"})
    public UserDto getUser(@ApiParam(name = "emailId", required = true) @PathVariable String emailId) {
        return userService.fetchUser(emailId);
    }

    //    Admin Operation
    @ApiOperation(value = "Get a User By UserName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping(value = "/get/{userName}", name = "This is used to get user details by email id")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public UserDto getUserDetail(@ApiParam(name = "userName", required = true) @PathVariable String userName) {
        return userService.getUserDetail(userName);
    }

    @ApiOperation(value = "Used to validate if user exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })

    @GetMapping({"/user/validate"})
    public Boolean validate(@ApiParam(name = "emailId", required = true) @RequestParam String emailId,
                            @ApiParam(name = "mobile", required = true) @RequestParam String mobile) {
        return userService.validateIfExist(emailId, mobile);
    }

}
