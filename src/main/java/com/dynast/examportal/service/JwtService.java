package com.dynast.examportal.service;

import com.dynast.examportal.configuration.AuthService;
import com.dynast.examportal.model.JwtRequest;
import com.dynast.examportal.model.JwtResponse;
import com.dynast.examportal.model.User;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JwtService implements UserDetailsService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;


    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String email = jwtRequest.getEmail();
        User user = loadUserByEmail(email);
        String userName = user.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);
        String newGeneratedToken = jwtUtil.generateToken(userName);
        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username: " + username)
                );
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
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
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
