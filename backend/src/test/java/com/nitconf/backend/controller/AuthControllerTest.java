package com.nitconf.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.slf4j.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.nitconf.backend.payload.request.LoginRequest;
import com.nitconf.backend.payload.request.SignupRequest;
import com.nitconf.backend.payload.response.JwtResponse;
import com.nitconf.backend.payload.response.MessageResponse;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.security.jwt.JwtUtils;
import com.nitconf.backend.security.services.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_validSignIn(){
        LoginRequest req=new LoginRequest();
        req.setEmail("test@mail.com");
        req.setPassword("test");
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl("123","testUser","test@mail.com","test",null);
        when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        ResponseCookie jwtCookie = mock(ResponseCookie.class);
        when(jwtCookie.getValue()).thenReturn("dummy_token");
        when(jwtUtils.generateJwtCookie(userDetails)).thenReturn(jwtCookie);
         ResponseEntity<?> responseEntity = authController.authenticateUser(req);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(JwtResponse.class, responseEntity.getBody().getClass());
        JwtResponse jwtResponse = (JwtResponse) responseEntity.getBody();
        assertEquals("testUser", jwtResponse.getUsername());
        assertEquals("test@mail.com", jwtResponse.getEmail());
        assertEquals("123", jwtResponse.getId());
        assertEquals("dummy_token", jwtResponse.getAccessToken());
        assertEquals("Bearer", jwtResponse.getTokenType());
    }


    @Test
    void test_invalidSignIn() {
        // Mocking login request
        LoginRequest req = new LoginRequest();
        req.setEmail("test@mail.com");
        req.setPassword("test");

        Authentication authentication = mock(Authentication.class);
        // Mocking authentication failure
        when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())))
            .thenReturn(null);


        // Calling the method under test
        ResponseEntity<?> responseEntity = authController.authenticateUser(req);

        // Asserting the response
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(MessageResponse.class, responseEntity.getBody().getClass());

        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertEquals("Invalid email or password", messageResponse.getMessage());
    }
    @Test
    void test_CreateUser(){
    when(userRepository.existsByUsername("testUser")).thenReturn(false);
    when(userRepository.existsByEmail("test@mail.com")).thenReturn(false);

    // Create a SignupRequest
    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setUsername("testUser");
    signupRequest.setEmail("test@mail.com");
    signupRequest.setPassword("testPassword");
    when(encoder.encode("password")).thenReturn("dummy");
    // Invoke the createUser method
    ResponseEntity<?> responseEntity = authController.createUser(signupRequest);

    // Assert the response
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertTrue(responseEntity.getBody() instanceof MessageResponse);
    assertEquals("Account created", ((MessageResponse) responseEntity.getBody()).getMessage());

    // Verify that userRepository.save() is called with any User object
    verify(userRepository).save(any());
    }
    @Test
    void test_CreateUser_DuplicateName(){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testUser");
        signupRequest.setEmail("test@mail.com");
        signupRequest.setPassword("testPassword");
        when(userRepository.existsByUsername("testUser")).thenReturn(true);

        ResponseEntity<?> responseEntity = authController.createUser(signupRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof MessageResponse);
        assertEquals("This username is taken", ((MessageResponse) responseEntity.getBody()).getMessage());


    }
    @Test
    void test_CreateUser_DuplicateEmail(){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testUser");
        signupRequest.setEmail("test@mail.com");
        signupRequest.setPassword("testPassword");
        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);


        ResponseEntity<?> responseEntity = authController.createUser(signupRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof MessageResponse);
        assertEquals("This email is taken", ((MessageResponse) responseEntity.getBody()).getMessage());


    }
    @Test
    void test_LogoutUser(){
        HttpServletRequest request= mock(HttpServletRequest.class);
        HttpServletResponse response= mock(HttpServletResponse.class);
        Logger logger=mock(Logger.class);
        authController.setLogger(logger);
        when(jwtUtils.getJwtFromCookies(request)).thenReturn("validJwtToken");
        when(jwtUtils.validateJwtToken("validJwtToken")).thenReturn(true);
        ResponseCookie jwtCookie = mock(ResponseCookie.class);
        when(jwtUtils.deleteJwtCookie()).thenReturn(jwtCookie);

        ResponseEntity<?> responseEntity = authController.logoutUser(request, response);
        verify(logger,atLeastOnce()).info("Received logout request.");
        verify(logger,atLeastOnce()).info("JWT is valid.");
        verify(logger,atLeastOnce()).info("Logout successful.");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Logout Successful", ((MessageResponse) responseEntity.getBody()).getMessage());
        verify(jwtUtils).deleteJwtCookie();
        verify(response).addHeader(any(), any());

    }
    @Test
    void test_LogoutUserInvalidJwt(){
        HttpServletRequest request= mock(HttpServletRequest.class);
        HttpServletResponse response= mock(HttpServletResponse.class);
        Logger logger=mock(Logger.class);
        authController.setLogger(logger);
        when(jwtUtils.getJwtFromCookies(request)).thenReturn("validJwtToken");
        when(jwtUtils.validateJwtToken("validJwtToken")).thenReturn(false);
        ResponseEntity<?> responseEntity = authController.logoutUser(request, response);
        verify(logger,atLeastOnce()).error("JWT is invalid.");
        verify(logger,atLeastOnce()).error("Invalid or expired token.");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid or expired token", ((MessageResponse) responseEntity.getBody()).getMessage());

    }
    @Test
    void test_LogoutUserNoCookie(){
        HttpServletRequest request= mock(HttpServletRequest.class);
        HttpServletResponse response= mock(HttpServletResponse.class);
        Logger logger=mock(Logger.class);
        authController.setLogger(logger);
        when(jwtUtils.getJwtFromCookies(request)).thenReturn(null);
        ResponseEntity<?> responseEntity = authController.logoutUser(request, response);
        verify(logger,atLeastOnce()).error("Invalid or expired token.");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid or expired token", ((MessageResponse) responseEntity.getBody()).getMessage());

    }
    @Test
    void test_LogoutUserExc() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doThrow(new RuntimeException("simulated exception")).when(jwtUtils).getJwtFromCookies(request);
    
        ResponseEntity<?> responseEntity = authController.logoutUser(request, response);
    
        // Asserting status code
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    
        // Extracting the message from the response body
        MessageResponse responseBody = (MessageResponse) responseEntity.getBody();
        String message = responseBody.getMessage();
    
        // Asserting the message
        assertEquals("Internal Server Error", message);
    
        // Verifying method invocation
        verify(jwtUtils, times(1)).getJwtFromCookies(request);
    }
}
