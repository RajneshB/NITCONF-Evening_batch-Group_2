package com.nitconf.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.nitconf.backend.models.User;
import com.nitconf.backend.payload.request.ForgotPasswordRequest;
import com.nitconf.backend.payload.request.PasswordResetRequest;
import com.nitconf.backend.payload.response.MessageResponse;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.security.services.ResetPasswordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/forgotPassword")
@CrossOrigin(origins="https://nitconf.vercel.app", maxAge = 3600)
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private ResetPasswordService resetPasswordService;

    
    /**
     * Sent Link:
     * <p>
     * Sent the reset link to the given email
     * @param request :{@link ForgotPasswordRequest}
     * @return :{@link MessageResponse}
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    @PostMapping("/sentlink")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request){
        String email= request.getEmail();
        Optional<User> u= userRepository.findByEmail(email);
        if(u!=null){
         
            String token=UUID.randomUUID().toString();
            resetPasswordService.updateResetPassword(token, email);
            resetPasswordService.sendResetLinkByEmail(email, token);

            return ResponseEntity.ok(new MessageResponse("Reset Link Sent to email"));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));

        }
    }
    /**
     * PasswordReset
     * <p>
     *  resets the password of a user
     * @param request : {@link PasswordResetRequest}
     * @param token : A valid token string
     * @return : {@link MessageResponse}
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    @PostMapping("/updatePassword")
    public ResponseEntity<?> passwordReset(@Valid @RequestBody PasswordResetRequest request,@RequestParam String token){
        String newPassword= request.getPassword();
        User user= resetPasswordService.get(token);
        if(user!=null){
            resetPasswordService.updatePassword(user, newPassword);
            return ResponseEntity.ok(new MessageResponse("PasswordSuccessfully changed"));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }

    }

}
