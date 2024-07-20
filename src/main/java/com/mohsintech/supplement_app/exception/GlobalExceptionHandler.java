package com.mohsintech.supplement_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SupplementNotFoundException.class)
    public ResponseEntity<ErrorObject> supplementNotFound(SupplementNotFoundException ex, WebRequest webRequest){
        ErrorObject error = ErrorObject.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .date(new Date())
                .build();

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }


}
