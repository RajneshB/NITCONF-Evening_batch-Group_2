package com.nitconf.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.nitconf.backend.models.User;
import com.nitconf.backend.payload.request.ForgotPasswordRequest;
import com.nitconf.backend.payload.request.PasswordResetRequest;
import com.nitconf.backend.payload.response.MessageResponse;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.security.services.ResetPasswordService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DataMongoTest
public class ForgotPasswordControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResetPasswordService resetPasswordService;

    @InjectMocks
    private ForgotPasswordController forgotPasswordController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testForgotPassword() {
        String email = "test@example.com";
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail(email);

        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(user));
        doNothing().when(resetPasswordService).updateResetPassword("validToken", "test@example.com");
        doNothing().when(resetPasswordService).sendResetLinkByEmail(email, "validToken");

        ResponseEntity<?> responseEntity = forgotPasswordController.forgotPassword(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof MessageResponse);
        assertEquals("Reset Link Sent to email", ((MessageResponse) responseEntity.getBody()).getMessage());
    }

    @Test
    public void testPasswordReset() {
        String token = "validToken";
        String newPassword = "newPassword";
        PasswordResetRequest request = new PasswordResetRequest();
        request.setPassword(newPassword);

        User user = new User();

        when(resetPasswordService.get(token)).thenReturn(user);
        doNothing().when(resetPasswordService).updatePassword(user, newPassword);

        ResponseEntity<?> responseEntity = forgotPasswordController.passwordReset(request, token);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof MessageResponse);
        assertEquals("PasswordSuccessfully changed", ((MessageResponse) responseEntity.getBody()).getMessage());
    }
    @Test
    public void testUserNotFoundForPasswordReset(){
        String token = "validToken";
        String newPassword = "newPassword";
        PasswordResetRequest request = new PasswordResetRequest();
        request.setPassword(newPassword);
        when(resetPasswordService.get(token)).thenReturn(null);
        ResponseEntity<?> responseEntity = forgotPasswordController.passwordReset(request, token);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User not found", ((MessageResponse) responseEntity.getBody()).getMessage());
    }
    @Test
    public void testUserByEmailNotFound(){
        String email = "test@example.com";
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(null);
        ResponseEntity<?> responseEntity = forgotPasswordController.forgotPassword(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof MessageResponse);
        assertEquals("User not found", ((MessageResponse) responseEntity.getBody()).getMessage());
    }
}

