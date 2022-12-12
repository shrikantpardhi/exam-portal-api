package com.dynast.examportal.controller;

import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.JwtRequest;
import com.dynast.examportal.model.JwtResponse;
import com.dynast.examportal.service.JwtService;
import com.dynast.examportal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@Api(value = "All user profile related APIs", tags = {"User Controller"})
@RequestMapping(value = "/api/v1/user/")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @ApiOperation(value = "Fetch list of users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("all")
    @PreAuthorize("hasRole('Admin')")
    Iterable<UserDto> getUsers() {
        LOGGER.info("inside getUsers{}");
        return userService.getUsers();
    }

    @ApiOperation(value = "Create an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 422, message = "Unable to create an user")
    })
    @PostMapping(value = {"create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponse createUser(@ApiParam(name = "user", required = true) @RequestBody UserDto user) throws Exception {
        LOGGER.info("inside createUser :" + user.getEmail());
        UserDto u = userService.createUser(user);
        if (Objects.nonNull(u)) {
            JwtRequest jwtRequest = new JwtRequest(user.getEmail(), user.getPassword());
            return jwtService.getToken(jwtRequest);
        } else {
            throw new UnprocessableEntityException("Unable to create an User");
        }
    }

    @ApiOperation(value = "Create an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 422, message = "Unable to create an user")
    })
    @PostMapping(value = {"create/educator"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createEducator(@ApiParam(name = "user", required = true) @RequestBody UserDto user) throws Exception {
        LOGGER.info("inside createEducator :" + user.getEmail());
        return userService.createEducator(user);
    }

    @ApiOperation(value = "update an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 422, message = "failed to updated")
    })
    @PutMapping(value = {"update"})
    @PreAuthorize("hasRole('User')")
    public UserDto updateUser(@ApiParam(name = "user", required = true) @RequestBody UserDto user) throws DataBaseException {
        LOGGER.info("inside updateUser :" + user.getEmail());
        return userService.updateUser(user);
    }

    @ApiOperation(value = "Get a User By Email Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping({"get/email/{emailId}"})
    @PreAuthorize("hasRole('Admin')")
    public UserDto getUser(@ApiParam(name = "emailId", required = true) @PathVariable String emailId) {
        LOGGER.info("inside getUser :" + emailId);
        return userService.getUserByEmailId(emailId);
    }

    //    Admin Operation
    @ApiOperation(value = "Get a User By user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping(value = "get/id/{userId}")
    @PreAuthorize("hasRole('Admin')")
    public UserDto getUserDetail(@ApiParam(name = "userId", required = true) @PathVariable String userId) {
        LOGGER.info("inside getUser :" + userId);
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "Used to validate if an user exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping({"validate"})
    public Boolean validate(@ApiParam(name = "emailId", required = true) @RequestParam String emailId,
                            @ApiParam(name = "mobile", required = true) @RequestParam String mobile) {
        LOGGER.info("inside validate - emailId: " + emailId + "mobile : " + mobile);
        return userService.validateIfExist(emailId, mobile);
    }

    @ApiOperation(value = "Reset Password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping({"reset-password"})
    public Boolean resetPassword(@ApiParam(name = "emailId", required = true) @RequestParam String emailId) {
        LOGGER.info("inside resetPassword : " + emailId);
        return userService.resetPassword(emailId);
    }

    @ApiOperation(value = "Change Password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Not found - The user was not found"),
            @ApiResponse(code = 422, message = "Unable to update password")
    })
    @GetMapping({"update-password"})
    public Boolean updatePassword(@ApiParam(name = "user", required = true) @RequestBody UserDto user) {
        LOGGER.info("inside updatePassword : " + user.getEmail());
        return userService.updatePassword(user);
    }

    @ApiOperation(value = "Update User Status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @GetMapping({"change-status"})
    public Boolean changeStatus(@ApiParam(name = "userId", required = true) @RequestParam String userId) {
        LOGGER.info("inside disable - userId: " + userId);
        return userService.changeStatus(userId);
    }
}
