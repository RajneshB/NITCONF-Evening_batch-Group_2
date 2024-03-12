package com.nitconf.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nitconf.backend.security.jwt.JwtUtils;
import com.nitconf.backend.service.StorageService;
import com.nitconf.backend.models.User;
import com.nitconf.backend.payload.response.JwtResponse;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.request.profileReq;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The controller class responsible for handling profile-related endpoints.
 * 
 * @author <a href="https://github.com/zEvoker">Johann B Simon</a>
 * @since 1.0
 */

@CrossOrigin(origins="http://localhost:5173", maxAge = 3600,allowCredentials = "true")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private UserRepository profRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StorageService service;

    /**
     * Retrieves the profile picture of the authenticated user.
     * 
     * @param request :{@link HttpServletRequest}
     * @return {@link ResponseEntity}
     */
    @GetMapping("/pic")
    public ResponseEntity<?> getProfilePic(HttpServletRequest request) {
        String jwt=jwtUtils.getJwtFromCookies(request);
        String mail=jwtUtils.getUsernameFromJwtToken(jwt);
        User user=profRepo.findByEmail(mail).orElseThrow();
        ByteArrayResource resource = service.downloadImage(user);
        return ResponseEntity.ok()
        .contentType(MediaType.valueOf("image/png"))
        .contentLength(resource.contentLength())
        .body(resource);
    }

    /**
     * Retrieves the profile information of the authenticated user.
     * 
     * @param request :{@link HttpServletRequest}
     * @return {@link ResponseEntity}
     */
    @GetMapping("")
    public ResponseEntity<User> getUserProfile(HttpServletRequest request) {
        String jwt=jwtUtils.getJwtFromCookies(request);
        String mail=jwtUtils.getUsernameFromJwtToken(jwt);
        User user=profRepo.findByEmail(mail).orElseThrow();
        return ResponseEntity.ok(user);
    }
    
    /**
     * Edits the profile information of the authenticated user.
     * 
     * @param request :{@link HttpServletRequest}
     * @param entity :{@link profileReq}
     * @return {@link ResponseEntity}
     */
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
            // if(entity.doj!=null)
            //     profile.setDoj(entity.doj);
            profRepo.save(profile);
            return ResponseEntity.ok("Updated successfully");
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Edits the profile picture of the authenticated user.
     * 
     * @param request :{@link HttpServletRequest}
     * @param profilePic :{@link MultipartFile}
     * @return {@link ResponseEntity}
     */
    @PutMapping("/pic")
    public ResponseEntity<?> editProfilePic(HttpServletRequest request, @RequestParam("profilePic") MultipartFile profilePic) {
        String jwt=jwtUtils.getJwtFromCookies(request);
        String mail=jwtUtils.getUsernameFromJwtToken(jwt);
        Optional<User> optionalProfile=profRepo.findByEmail(mail);
        User profile = optionalProfile.get();
        MultipartFile profilePicFile = profilePic;
        if (profilePicFile != null) {
            String uploadResult = service.uploadImage(profilePicFile,profile);
            if (!uploadResult.equals("successful")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile picture: " + uploadResult);
            }
        }
        return ResponseEntity.ok(profile);
    }
    
}
