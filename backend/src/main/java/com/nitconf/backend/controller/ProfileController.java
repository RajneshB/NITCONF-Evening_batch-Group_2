package com.nitconf.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nitconf.backend.security.jwt.JwtUtils;
import com.nitconf.backend.models.User;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.request.profileReq;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins="http://localhost:5173", maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private UserRepository profRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String jwt=jwtUtils.getJwtFromCookies(request);
        String mail=jwtUtils.getUsernameFromJwtToken(jwt);
        System.out.println(mail);
        Optional<User> user=profRepo.findByEmail(mail);
        if(user==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PutMapping("")
    public ResponseEntity<Object> editProfile(HttpServletRequest request, @RequestBody profileReq entity) {
        String jwt=jwtUtils.getJwtFromCookies(request);
        String mail=jwtUtils.getUsernameFromJwtToken(jwt);
        Optional<User> optionalProfile=profRepo.findByEmail(mail);
        User profile = optionalProfile.get();
        
        if(profile!=null){
            if(entity.name!=null)
                profile.setUsername(entity.name);
            if(entity.mail!=null)
                profile.setEmail(entity.mail);
            if(entity.contact!=null)
                profile.setContact(entity.contact);
            if(entity.profession!=null)
                profile.setProfession(entity.profession);
            if(entity.doj!=null)
                profile.setDoj(entity.doj);
            profRepo.save(profile);
            return ResponseEntity.ok("Updated successfully");
        }
        return ResponseEntity.badRequest().build();
    }
    
}
