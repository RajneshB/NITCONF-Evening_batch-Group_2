package com.nitconf.backend.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "users")
public class User {
  @Id
  private String id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private byte[] profilePic;

  private String resetPasswordToken;




  public User() {
    }

    public User(String username, String email, String password,byte[] profilePic) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.profilePic=profilePic;

    }


    public String getId() {
    return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
      }
      public void setId(String newId) {
         this.id=newId;
      }
    
      public void setUsername(String newName) {
        this.username=newName;
     }
     public void setEmail(String newEmail) {
      this.email=newEmail;
    }
    public void setPassword(String newPass) {
      this.password=newPass;
 }
 public byte[] getProfilePic() {
  return profilePic;
}

public void setProfilePic(byte[] profilePic) {
  this.profilePic = profilePic;
}
public String getResetPasswordToken(){
  return resetPasswordToken;
}
public void setResetPasswordToken(String resetPasswordToken){
  this.resetPasswordToken=resetPasswordToken;
}

}