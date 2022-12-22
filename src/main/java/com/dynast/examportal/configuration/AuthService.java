package com.dynast.examportal.configuration;

import com.dynast.examportal.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AuthService {
    public List<String> getRoles(User user) {
        List<String> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add("ROLE_" + role.getRoleName()));
        return authorities;
    }


}
