package com.nitconf.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.nitconf.backend.models.User;
import com.nitconf.backend.payload.request.ForgotPasswordRequest;
import com.nitconf.backend.payload.response.MessageResponse;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.security.services.ResetPasswordService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/forgotPassword")
@CrossOrigin(origins="http://localhost:5173", maxAge = 3600)
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private ResetPasswordService resetPasswordService;
    
    @PostMapping("/sentlink")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        String email= request.getEmail();
        Optional<User> u= userRepository.findByEmail(email);
        if(u.isPresent()){
         
            String token=UUID.randomUUID().toString();
            resetPasswordService.updateResetPassword(token, email);
            resetPasswordService.sendResetLinkByEmail(email, token);

            return ResponseEntity.ok(new MessageResponse("Reset Link Sent to email"));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));

        }
    }

}
