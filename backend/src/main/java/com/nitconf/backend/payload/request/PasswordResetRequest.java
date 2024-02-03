package com.nitconf.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequest {
    
    @NotBlank
    private String password;

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }
}
