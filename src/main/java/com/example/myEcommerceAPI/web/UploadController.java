package com.example.myEcommerceAPI.web;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.DataTransferObjects.ImageUploadRequest;
import com.example.myEcommerceAPI.service.CloudinaryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {
    @Autowired
    private CloudinaryService cloudinaryService;


    @PostMapping("/upload-base64")
    public ResponseEntity<?> uploadImage(@Valid @RequestBody ImageUploadRequest request) {
        System.err.println("Received request: " + request.toString());
        String base64Image = request.getImage();
        if (base64Image == null || base64Image.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No image provided"));
        }
        try {
            String imageUrl = cloudinaryService.uploadBase64Image(base64Image);
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Image upload failed"));
        }
    }
    
}
