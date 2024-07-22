package com.mohsintech.supplement_app.dto;

import lombok.Data;
//form for user registration
//(WILL ADD VALIDATION SOON)
@Data
public class RegisterDto {
    private String username;
    private String password;
}
