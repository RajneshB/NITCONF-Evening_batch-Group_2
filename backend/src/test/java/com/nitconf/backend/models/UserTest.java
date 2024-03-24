package com.nitconf.backend.models;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class UserTest {

    @Test
    public void testNoArgsConstructor() {
        // Create an instance using the no-args constructor
        User user = new User();

        // Verify that all fields are null
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getContact());
        assertNull(user.getProfession());
        assertNull(user.getDoj());
        assertNull(user.getProfilePic());
        assertNull(user.getResetPasswordToken());
    }

    @Test
    public void testAllArgsConstructor() {
        // Test data
        String username = "john_doe";
        String email = "john@example.com";
        String password = "password";
        String contact = "1234567890";
        String profession = "Engineer";
        Date doj = new Date();
        byte[] profilePic = new byte[10];

        // Create an instance with all fields initialized
        User user = new User(username, email, password, contact, profession, doj, profilePic);

        // Verify that all fields are correctly set
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(contact, user.getContact());
        assertEquals(profession, user.getProfession());
        assertEquals(doj, user.getDoj());
        assertEquals(profilePic, user.getProfilePic());
    }

    @Test
    public void testSettersAndGetters() {
        // Create an instance
        User user = new User();

        // Set values using setters
        user.setId("123");
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setContact("1234567890");
        user.setProfession("Engineer");
        Date doj = new Date();
        user.setDoj(doj);
        byte[] profilePic = new byte[10];
        user.setProfilePic(profilePic);

        // Verify values using getters
        assertEquals("123", user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("1234567890", user.getContact());
        assertEquals("Engineer", user.getProfession());
        assertEquals(doj, user.getDoj());
        assertEquals(profilePic, user.getProfilePic());
    }
}
