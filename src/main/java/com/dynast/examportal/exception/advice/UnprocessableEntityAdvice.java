package com.dynast.examportal.exception.advice;

import com.dynast.examportal.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UnprocessableEntityAdvice {
    @ResponseBody
    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String unprocessableEntityHandler(UnprocessableEntityException ex) {
        return ex.getMessage();
    }
}
