package com.dynast.examportal.service.Impl;

import com.dynast.examportal.configuration.AuthService;
import com.dynast.examportal.model.JwtRequest;
import com.dynast.examportal.model.JwtResponse;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.service.JwtService;
import com.dynast.examportal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements UserDetailsService, JwtService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Override
    public JwtResponse getToken(JwtRequest jwtRequest) throws Exception {
        User user = loadUserByEmail(jwtRequest.getEmail());
        authenticate(user.getUserName(), jwtRequest.getPassword());
        user.setPassword("");
        return new JwtResponse(user, jwtUtil.generateToken(user.getUserName()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username: " + username)
                );
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authService.getRoles(user).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

    private User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            System.out.println(e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public ResponseEntity refresh(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        if(jwtUtil.validateToken(token, username)){
            return ResponseEntity.ok(jwtUtil.generateToken(username));
        }
        return null;
    }
}
