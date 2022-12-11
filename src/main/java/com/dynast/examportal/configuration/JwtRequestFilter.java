package com.dynast.examportal.configuration;

import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private com.dynast.examportal.util.User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws MalformedJwtException, ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtUtil.getEmailFromToken(jwtToken);
                user.setEmail(email);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }


        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), jwtToken, email);
        }
        filterChain.doFilter(request, response);

    }

    private void setSecurityContext(WebAuthenticationDetails buildDetails, String jwtToken, String email) {
        if (jwtUtil.validateToken(jwtToken, email)) {
            com.dynast.examportal.model.User user = userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("User not found." + email));
            List<String> roles = authService.getRoles(user);
            final UserDetails userDetails = new User(email, "", roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(buildDetails);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

}
