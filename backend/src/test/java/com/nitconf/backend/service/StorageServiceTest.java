package com.nitconf.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import com.nitconf.backend.models.User;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.util.ImageUtils;

public class StorageServiceTest {

    @Test
    public void testUploadImage_Successful() throws IOException {
        // Mocks
        UserRepository userRepository = mock(UserRepository.class);
        StorageService storageService = new StorageService();
        storageService.repository = userRepository;

        // Test data
        User user = new User();
        MultipartFile file = new MockMultipartFile("test.png", "test.png", "image/png", "test data".getBytes());

        // Stubbing
        when(userRepository.save(user)).thenReturn(user);

        // Method call
        String result = storageService.uploadImage(file, user);

        // Verifications
        verify(userRepository).save(user);
        assertEquals("successful", result);
    }

    @Test
    public void testUploadImage_Exception() throws IOException {
        // Mocks
        UserRepository userRepository = mock(UserRepository.class);
        StorageService storageService = new StorageService();
        storageService.repository = userRepository;

        // Test data
        User user = new User();
        MultipartFile file = new MockMultipartFile("test.png", "test.png", "image/png", "test data".getBytes());

        // Stubbing to throw an exception
        when(userRepository.save(user)).thenThrow(new RuntimeException("Some database error"));

        // Method call
        String result = storageService.uploadImage(file, user);

        // Verifications
        verify(userRepository).save(user);
        assertEquals("Error uploading image: Some database error", result);
    }

    @Test
public void testDownloadImage() throws Exception {
    // Create a user with a profile picture
    User user = new User();
    byte[] compressedImage = ImageUtils.compressImage(new byte[11]);
    user.setProfilePic(compressedImage);

    // Mock the UserRepository
    UserRepository userRepository = mock(UserRepository.class);
    // Create an instance of StorageService
    StorageService storageService = new StorageService();
    // Set the UserRepository mock as a dependency of StorageService
    storageService.repository = userRepository;

    // Stubbing the findByEmail method to return the user
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    // Call the downloadImage method
    ByteArrayResource byteArrayResource = storageService.downloadImage(user);

    // Verify that the compressed image is decompressed and returned
    assertNotNull(byteArrayResource);
    assertEquals(compressedImage.length, byteArrayResource.contentLength());
}

}
