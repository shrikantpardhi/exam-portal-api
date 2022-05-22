package com.dynast.examportal.exception.advice;

import com.dynast.examportal.exception.UserCreationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserCreationFailedAdvice {
    @ResponseBody
    @ExceptionHandler(UserCreationFailedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String userProcessableFailed(UserCreationFailedException ex) {
        return ex.getMessage();
    }

}
