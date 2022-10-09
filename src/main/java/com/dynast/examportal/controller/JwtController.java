package com.dynast.examportal.controller;

import com.dynast.examportal.model.JwtRequest;
import com.dynast.examportal.model.JwtResponse;
import com.dynast.examportal.service.JwtService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(value = "Authentication APIs", tags = {"Authentication Controller"})
@RequestMapping(value = "/api/v1/auth/")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception exception) {
        return new ResponseEntity<>("Oops, Exception occurred while logging in!", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping({"login"})
    public JwtResponse getToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.getToken(jwtRequest);
    }

    @PostMapping({"refresh"})
    public ResponseEntity refreshJwtToken(@RequestBody String token) throws Exception {
        return jwtService.refresh(token);
    }
}

