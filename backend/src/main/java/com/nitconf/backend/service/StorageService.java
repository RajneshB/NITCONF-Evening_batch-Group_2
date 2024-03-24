package com.nitconf.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nitconf.backend.models.User;
import com.nitconf.backend.repository.UserRepository;
import com.nitconf.backend.util.ImageUtils;

@Service
public class StorageService {
    
    @Autowired
    public UserRepository repository;
    
    public String uploadImage(MultipartFile file,User user){
        try{    
            user.setProfilePic(ImageUtils.compressImage(file.getBytes()));
            repository.save(user);
            return "successful";
        }catch(Exception e){
            return "Error uploading image: " + e.getMessage();
        }
    }

    @SuppressWarnings("null")
    public ByteArrayResource downloadImage(User user){
        ByteArrayResource pfp=new ByteArrayResource(ImageUtils.decompressImage(user.getProfilePic()));
        return pfp;
    }
}
