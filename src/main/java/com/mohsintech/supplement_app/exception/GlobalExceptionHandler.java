package com.mohsintech.supplement_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorObject> supplementNotFound(NotFoundException ex, WebRequest webRequest){
        ErrorObject error = ErrorObject.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .date(new Date())
                .build();

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<ErrorObject> supplementUpdateFailedFound(UpdateFailedException ex, WebRequest webRequest){
        ErrorObject error = ErrorObject.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .date(new Date())
                .build();

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


}
