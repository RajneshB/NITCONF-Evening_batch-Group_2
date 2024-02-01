package com.nitconf.backend.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

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

  private String contact;

  private String profession;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private java.util.Date doj;

  private byte[] profilePic;


  public User() {
    }

    public User(String username, String email, String password, String contact, String profession, java.util.Date doj, byte[] profilePic) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.contact=contact;
    this.profession=profession;
    this.doj=doj;
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

    public String getContact(){
      return contact;
    }

    public String getProfession() {
      return profession;
    }

    public java.util.Date getDoj() {
      return doj;
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

    public void setContact(String newContact) {
      this.contact=newContact;
    }

    public void setProfession(String newProfession) {
      this.profession=newProfession;
    }

    public void setDoj(java.util.Date newDoj) {
      this.doj=newDoj;
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
}