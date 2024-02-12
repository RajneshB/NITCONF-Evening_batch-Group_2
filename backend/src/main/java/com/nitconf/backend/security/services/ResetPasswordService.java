package com.nitconf.backend.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nitconf.backend.models.User;
import com.nitconf.backend.repository.UserRepository;


@Service
public class ResetPasswordService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * update Reset token:
     * This sets the reset token when requesting the sent link request
     * 
     * @param token :A token string
     * @param email :An email string
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    public void  updateResetPassword(String token,String email){
        Optional<User> user  = userRepository.findByEmail(email);
        if(user.isPresent()){
            User userI=user.get();
            userI.setResetPasswordToken(token);
            userRepository.save(userI);
        }else{
            throw new UsernameNotFoundException("User with the given email was not found");
        }

    }
    /**
     * get:
     * This gives the user which has the given reset token
     * @param resetPasswordToken :A reset token string
     * @return {@link User}
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    public User get(String resetPasswordToken){
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }
    /**
     * update password
     * <p>
     * updates the password of the user with the new password
     * @param user :{@link User}
     * @param newpassword :A new password
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    public void updatePassword(User user,String newpassword){
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        String encodedPassword= passwordEncoder.encode(newpassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
    /**
     * send Reset Link By Email
     * <p>
     * send reset link to the given email
     * 
     * @param email :An email string 
     * @param token :A token string 
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    public void sendResetLinkByEmail(String email,String token){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset Link");
        mailMessage.setText("Click the link below to reset your password:\n\n"
                + "http://localhost:5173/#/reset-password?token=" + token);
        javaMailSender.send(mailMessage);

    }
}
