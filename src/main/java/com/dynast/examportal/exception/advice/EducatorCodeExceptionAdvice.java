package com.dynast.examportal.exception.advice;

import com.dynast.examportal.exception.EducatorCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EducatorCodeExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(EducatorCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundHandler(EducatorCodeNotFoundException ex) {
        return ex.getMessage();
    }
}
