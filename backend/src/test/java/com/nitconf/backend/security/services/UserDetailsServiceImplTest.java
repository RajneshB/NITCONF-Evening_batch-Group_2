package com.nitconf.backend.security.services;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.nitconf.backend.models.User;
import com.nitconf.backend.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;


    @Test
    void testLoadUserByUsername() {
        String email="testing@example.com";
        User user= new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails userDetails=userDetailsService.loadUserByUsername(email);

        assertTrue(userDetails.isEnabled());
        verify(userRepository,times(1)).findByEmail(email);
        
    }
}
