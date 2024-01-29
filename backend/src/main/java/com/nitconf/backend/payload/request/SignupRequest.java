package com.nitconf.backend.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank
    @Size(max=20)
    private String username;

    @NotBlank
    @Size(min=6,max=20)
    private String password;

    @NotBlank
    @Email
    private String email;

    public String getUsername(){
        return username;

    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setEmail(String email){
        this.email=email;
    }
}
