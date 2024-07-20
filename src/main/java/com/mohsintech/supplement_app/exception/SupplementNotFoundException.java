package com.mohsintech.supplement_app.exception;

public class SupplementNotFoundException extends RuntimeException{
    public SupplementNotFoundException(String message){
        super(message);
    }
}
