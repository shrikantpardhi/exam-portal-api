package com.dynast.examportal.util;

import org.springframework.stereotype.Component;

@Component
public class User {

    //	Optional<String> username = Optional.ofNullable(null);
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
