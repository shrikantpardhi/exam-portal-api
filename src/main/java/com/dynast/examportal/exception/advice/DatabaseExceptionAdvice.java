package com.dynast.examportal.exception.advice;

import com.dynast.examportal.exception.DataBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class DatabaseExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(DataBaseException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String databaseExceptionHandler(DataBaseException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String databaseExceptionHandler(SQLIntegrityConstraintViolationException ex) {
        return ex.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String databaseExceptionHandler(ConstraintViolationException ex) {
        return ex.getMessage();
    }
}
