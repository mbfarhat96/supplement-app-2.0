package com.mohsintech.supplement_app.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObject {

    private Integer statusCode;
    private String message;
    private Date date;
}
