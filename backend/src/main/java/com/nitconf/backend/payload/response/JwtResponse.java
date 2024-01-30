package com.nitconf.backend.payload.response;
public class JwtResponse {
    private String id;
    private String username;
    private String email;
    private String token;
    private String tokenType="Bearer";

    public JwtResponse(String username,String email,String id,String token,String tokenType){
        this.username=username;
        this.email=email;
        this.token=token;
        this.tokenType=tokenType;
        this.id=id;

    }
    public String getAccessToken(){
        return token;
    }
    public void setAccessToken(String newToken){
        this.token=newToken;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String newName){
        this.username=newName;
    }
    public String getId(){
        return id;
    }
    public void setId(String newId){
        this.id=newId;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String newEmail){
        this.email=newEmail;
    }
    public String getTokenType(){
        return tokenType;
    }
    public void setTokenType(String newType){
        this.tokenType=newType;
    }

}

