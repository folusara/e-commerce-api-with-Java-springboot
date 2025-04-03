package com.example.myEcommerceAPI.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.AllArgsConstructor;

// @AllArgsConstructor
@Service
public class CloudinaryService {
    
    private Cloudinary cloudinary;
    public CloudinaryService(@Lazy Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public String uploadBase64Image(String base64Image) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(base64Image, ObjectUtils.emptyMap());
        System.err.println("upload result" + uploadResult);
        return uploadResult.get("url").toString();
    }
}
