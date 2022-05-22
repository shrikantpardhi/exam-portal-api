package com.dynast.examportal.exception;

public class SubjectNotFoundException extends RuntimeException {

    public SubjectNotFoundException(String message) {
        super("Could not found the subject!");
    }
}
