package com.dynast.examportal.exception;

public class UserCreationFailedException extends RuntimeException {
    public UserCreationFailedException(String message) {
        super("Unprocessable Request" + message);
    }
}
