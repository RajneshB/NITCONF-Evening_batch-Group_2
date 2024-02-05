package com.nitconf.backend.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.nitconf.backend.models.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@DataMongoTest
public class UserRepositoryTest {


    @Autowired 
    private UserRepository userRepository;

    private String id;

    @AfterEach
    public void tearDown(){
        if(id!=null){
            userRepository.deleteById(id);
        }
    }


    @Test
    public void testFindByUsername() {
        // Arrange
        String username = "test2user";
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByUsername(username);

        // Assert
        assertTrue(foundUser.isPresent());
        assertTrue(foundUser.get().getUsername().equals(username));
        id=foundUser.get().getId();
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        String email = "test2@example.com";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail(email);

        // Assert
        assertTrue(foundUser.isPresent());
        assertTrue(foundUser.get().getEmail().equals(email));
        id=foundUser.get().getId();

    }
    @Test
    public void testFindByResetToken() {
        // Arrange
        String resetToken = "testTestTestTestTestTestTestTestTest";
        User user = new User();
        user.setResetPasswordToken(resetToken);
        userRepository.save(user);

        // Act
        User foundUser = userRepository.findByResetPasswordToken(resetToken);

        // Assert
        assertTrue(foundUser!=null);
        assertTrue(foundUser.getResetPasswordToken().equals(resetToken));
        id=foundUser.getId();

    }
    @Test
    public void testExistsByUsername(){
        String username="testUser";
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);

        Boolean result=userRepository.existsByUsername(username);

        assertTrue(result);
        id=user.getId();
    }
    @Test
    public void testExistsByEmailUserExists(){
        String email="test@example.com";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);

        Boolean result=userRepository.existsByEmail(email);

        assertTrue(result);
        id=user.getId();
    }
    @Test
    public void testExistsByEmailUserNotExists(){
        String email="nonexistent@example.com";
        Boolean result=userRepository.existsByEmail(email);
        assertFalse(result);

    }
    @Test
    public void testExistsByUsernameUserNotExists(){
        String username="nonexistentUsername";
        Boolean result=userRepository.existsByUsername(username);
        assertFalse(result);

    }
    @Test
    public void testFindByNullUserName(){
        Optional<User> user=userRepository.findByUsername(null);
        assertFalse(user.isPresent());
    }
    @Test
    public void testFindByNullEmail(){
        Optional<User> user=userRepository.findByEmail(null);
        assertFalse(user.isPresent());
    }

}
