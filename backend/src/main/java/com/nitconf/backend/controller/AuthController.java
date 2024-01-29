

package com.nitconf.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nitconf.backend.models.User;
import com.nitconf.backend.payload.request.LoginRequest;
import com.nitconf.backend.payload.request.SignupRequest;
import com.nitconf.backend.payload.response.JwtResponse;
import com.nitconf.backend.payload.response.MessageResponse;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.security.jwt.JwtUtils;
import com.nitconf.backend.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;




@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
            ResponseCookie jwtCookie =jwtUtils.generateJwtCookie(userDetails);
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie.toString())

            .body(new JwtResponse (
                userDetails.getUsername(),
                userDetails.getEmail(),
                   userDetails.getId(),
                   authentication.toString(),
                   "Bearer"
            ));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest){
            if(userRepository.existsByUsername(signupRequest.getUsername())){
                return ResponseEntity.badRequest().body(new MessageResponse("This username is taken"));
            }
            if(userRepository.existsByEmail(signupRequest.getEmail())){
                return ResponseEntity.badRequest().body(new MessageResponse("This email is taken"));
            }

            byte[] defaultPic=loadDefaultImageContent();
            User user=new User(signupRequest.getUsername(), 
            signupRequest.getEmail(),
            encoder.encode(signupRequest.getPassword()),defaultPic);


            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Account created"));


    }
private byte[] loadDefaultImageContent() {
    try {
        Resource resource = new ClassPathResource("assets/Untitled.png");
        return StreamUtils.copyToByteArray(resource.getInputStream());
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}

