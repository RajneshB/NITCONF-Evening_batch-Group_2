package com.nitconf.backend.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ProfileReqTest {

    @Test
    public void testNoArgsConstructor() {
        // Create an instance using the no-args constructor
        profileReq req = new profileReq();

        // Verify that all fields are null
        assertNull(req.name);
        assertNull(req.mail);
        assertNull(req.contact);
        assertNull(req.profession);
    }

    @Test
    public void testAllArgsConstructor() {
        // Create an instance with all fields initialized
        String name = "John Doe";
        String mail = "john.doe@example.com";
        String contact = "1234567890";
        String profession = "Engineer";
        profileReq req = new profileReq(name, mail, contact, profession);

        // Verify that all fields are correctly set
        assertEquals(name, req.name);
        assertEquals(mail, req.mail);
        assertEquals(contact, req.contact);
        assertEquals(profession, req.profession);
    }
}
