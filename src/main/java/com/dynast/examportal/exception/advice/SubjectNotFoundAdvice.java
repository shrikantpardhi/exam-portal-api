package com.dynast.examportal.exception.advice;

import com.dynast.examportal.exception.SubjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SubjectNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SubjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String subjectNotFound(SubjectNotFoundException ex) {
        return ex.getLocalizedMessage();
    }
}
