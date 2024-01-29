

package com.nitconf.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPassword(String newPass){
        this.password=newPass;
    }
}
