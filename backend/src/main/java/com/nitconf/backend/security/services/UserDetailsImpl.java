package com.nitconf.backend.security.services;


import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nitconf.backend.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class UserDetailsImpl implements UserDetails {
    // private final long SerialVersionUID =1L;

    private String id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
    private byte[] profilePic;
    public UserDetailsImpl(String id,String username,String email,String password,byte[] profilePic){
        this.id=id;
        this.username=username;
        this.profilePic=profilePic;
        this.email=email;
        this.password=password;

    }


    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getProfilePic()    
            );
        }
        
        public String getId() {
            return id;
        }
        
        public String getEmail() {
            return email;
        }
        
        @Override
        public String getPassword() {
            return password;
        }
        
        @Override
        public String getUsername() {
            return username;
        }
        public byte[] getProfilePic() {
            return profilePic;
        }
    
        public void setProfilePic(byte[] profilePic) {
            this.profilePic = profilePic;
        }


        @Override
        public boolean isAccountNonExpired(){
            return true;
        }
      @Override
      public boolean isAccountNonLocked(){
        return true;
      }
      @Override
      public boolean isCredentialsNonExpired(){
        return true;
      }
      @Override
      public boolean isEnabled(){
        return true;
      }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
  }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;

        }
        if(o==null || this.getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl user =(UserDetailsImpl) o;
        return Objects.equals(id,user.id);  
    }
}


