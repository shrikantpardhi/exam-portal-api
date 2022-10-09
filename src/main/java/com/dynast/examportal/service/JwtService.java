package com.dynast.examportal.service;

import com.dynast.examportal.model.JwtRequest;
import com.dynast.examportal.model.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface JwtService {
    JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception;

    ResponseEntity refersh(String token);
}
