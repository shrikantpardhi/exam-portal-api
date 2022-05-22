package com.dynast.examportal.exception;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Could not find User " + email);
    }
}
