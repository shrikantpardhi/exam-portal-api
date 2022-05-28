package com.dynast.examportal.controller;

import com.dynast.examportal.model.JwtRequest;
import com.dynast.examportal.model.JwtResponse;
import com.dynast.examportal.service.JwtService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "Authentication APIs", tags = {"Authentication Controller"})
@RequestMapping(value = "/api/v1/")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping({"authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}

