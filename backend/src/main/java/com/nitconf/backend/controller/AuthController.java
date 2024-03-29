

package com.nitconf.backend.controller;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;




@CrossOrigin(origins="https://nitconf.vercel.app", maxAge = 3600,allowCredentials = "true")
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
    private static Logger logger =LoggerFactory.getLogger(AuthController.class);

    void setLogger(Logger logger){
        this.logger=logger;
    }

    
    /**
     * login
     * <p>
     *  logs in a user 
     * @param loginRequest :{@link LoginRequest}
     * @return {@link JwtResponse}
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            if(authentication==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Invalid email or password"));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
            ResponseCookie jwtCookie =jwtUtils.generateJwtCookie(userDetails);
            String token = jwtCookie.getValue();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie.toString())

            .body(new JwtResponse (
                userDetails.getUsername(),
                userDetails.getEmail(),
                   userDetails.getId(),
                   token,
                   "Bearer"
            ));
    }
    /**
     * create user
     * <p>
     * registers a new user 
     * @param signupRequest :{@link SignupRequest}
     * @return {@link MessageResponse}
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest){
            if(userRepository.existsByUsername(signupRequest.getUsername())){
                return ResponseEntity.badRequest().body(new MessageResponse("This username is taken"));
            }
            if(userRepository.existsByEmail(signupRequest.getEmail())){
                return ResponseEntity.badRequest().body(new MessageResponse("This email is taken"));
            }

            byte[] defaultPic=jwtUtils.loadDefaultImageContent();
            User user=new User(signupRequest.getUsername(), 
            signupRequest.getEmail(),
            encoder.encode(signupRequest.getPassword()),
            "",
            "",
            new Date(),
            defaultPic
            );


            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Account created"));


    }
    /**
     * logout
     * <p>
     *  logs out the user
     * @param request :{@link HttpServletRequest}
     * @param response :{@link HttpServletResponse}
     * @return :{@link MessageResponse}
     * @since 1.0
     * @author <a href="https://github.com/RajneshB">Rajnesh B</a>
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response){
        try{
            logger.info("Received logout request.");
            String jwt= jwtUtils.getJwtFromCookies(request);
            // logger.info("Received JWT: {}", jwt);
            if (jwt != null) {
                if (jwtUtils.validateJwtToken(jwt)) {
                    // Valid token
                    logger.info("JWT is valid.");
                } else {
                    // Invalid token
                    logger.error("JWT is invalid.");
                }
            }
            if(jwt!=null && jwtUtils.validateJwtToken(jwt)){
                SecurityContextHolder.clearContext();

                ResponseCookie  jwtCookie=jwtUtils.deleteJwtCookie();
                response.addHeader(HttpHeaders.SET_COOKIE,jwtCookie.toString());
                logger.info("Logout successful.");
                return  ResponseEntity.ok(new MessageResponse("Logout Successful"));
            } else {
                logger.error("Invalid or expired token.");
                return ResponseEntity.badRequest().body(new MessageResponse("Invalid or expired token"));
            }
        }catch(Exception e){
             logger.error("Exception during logout:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Internal Server Error"));
    
        }
    }

}