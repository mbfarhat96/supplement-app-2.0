package com.mohsintech.supplement_app.dto;

import lombok.Data;
//form for user login
//(WILL ADD VALIDATION SOON)
@Data
public class LoginDto {
    private String username;
    private String password;
}
