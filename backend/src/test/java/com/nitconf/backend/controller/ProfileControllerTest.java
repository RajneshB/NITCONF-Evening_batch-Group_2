package com.nitconf.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.nitconf.backend.models.User;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.request.profileReq;
import com.nitconf.backend.security.jwt.JwtUtils;
import com.nitconf.backend.service.StorageService;

import jakarta.servlet.http.HttpServletRequest;

@DataMongoTest
public class ProfileControllerTest {
    @Mock
    private UserRepository profRepo;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private StorageService service;

    @InjectMocks
    private ProfileController profileController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProfilePic() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String jwt = "valid_jwt";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(jwt);
        when(jwtUtils.getUsernameFromJwtToken(jwt)).thenReturn(email);
        when(profRepo.findByEmail(email)).thenReturn(Optional.of(user));
        ByteArrayResource resource = new ByteArrayResource(new byte[] {});
        when(service.downloadImage(user)).thenReturn(resource);
        
        ResponseEntity<?> responseEntity = profileController.getProfilePic(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ByteArrayResource);
    }

    @Test
    public void testGetUserProfile() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String jwt = "valid_jwt";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(jwt);
        when(jwtUtils.getUsernameFromJwtToken(jwt)).thenReturn(email);
        when(profRepo.findByEmail(email)).thenReturn(Optional.of(user));
        
        ResponseEntity<User> responseEntity = profileController.getUserProfile(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testEditProfile() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String jwt = "valid_jwt";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        
        // Creating a mock instance of profileReq
        profileReq entity = new profileReq();
        entity.name = "John Doe";
        entity.mail = "john.doe@example.com";
        
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(jwt);
        when(jwtUtils.getUsernameFromJwtToken(jwt)).thenReturn(email);
        when(profRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(profRepo.save(user)).thenReturn(user); // Mocking the behavior of the save method
        
        ResponseEntity<Object> responseEntity = profileController.editProfile(request, entity);
    
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Updated successfully", responseEntity.getBody());
    }

    @Test
    public void testEditProfileUserNotFound() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String jwt = "valid_jwt";
        String email = "test@example.com";
        
        // Creating a mock instance of profileReq
        profileReq entity = new profileReq();
        entity.name = "John Doe";
        entity.mail = "john.doe@example.com";
        
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(jwt);
        when(jwtUtils.getUsernameFromJwtToken(jwt)).thenReturn(email);
        when(profRepo.findByEmail(email)).thenReturn(Optional.empty());
        
        ResponseEntity<Object> responseEntity = profileController.editProfile(request, entity);
    
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testEditProfilePic() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String jwt = "valid_jwt";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        MultipartFile profilePic = new MockMultipartFile("file", "filename.png", "image/png", "test data".getBytes());
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(jwt);
        when(jwtUtils.getUsernameFromJwtToken(jwt)).thenReturn(email);
        when(profRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(service.uploadImage(profilePic, user)).thenReturn("successful");
        
        ResponseEntity<?> responseEntity = profileController.editProfilePic(request, profilePic);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testEditProfilePicError() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String jwt = "valid_jwt";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        MultipartFile profilePic = new MockMultipartFile("file", "filename.png", "image/png", "test data".getBytes());
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(jwt);
        when(jwtUtils.getUsernameFromJwtToken(jwt)).thenReturn(email);
        when(profRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(service.uploadImage(profilePic, user)).thenReturn("Error uploading profile picture");
        
        ResponseEntity<?> responseEntity = profileController.editProfilePic(request, profilePic);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error uploading profile picture: Error uploading profile picture", responseEntity.getBody());
    }
}
