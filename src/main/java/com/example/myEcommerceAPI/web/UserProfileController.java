package com.example.myEcommerceAPI.web;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.DataTransferObjects.UserProfileRequest;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.service.UserProfileService;
import com.example.myEcommerceAPI.service.UserService;
import com.example.myEcommerceAPI.utils.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserProfileController {

    UserProfileRepository userProfileRepository;
    UserProfileService userProfileService;
    UserRepository userRepository;
    UserService UserService;
    JWTUtil JWTUtil;
    
    @PostMapping("/create-user-profile")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserProfile userProfile, HttpServletRequest request,
            String token) {
        Optional<UserProfile> existingProfile = userProfileRepository.findByEmailIgnoreCase(userProfile.getEmail());
        if (existingProfile.isPresent()) {
            return new ResponseEntity<>("Profile already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        Long userId = JWTUtil.extractUserId(request);
        System.err.println(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Optional<UserProfile> existingUserId = Optional.ofNullable(userProfileRepository.findByUser(user));
        Optional<UserProfile> existingUserId = userProfileRepository.findByUser(user);
        if (existingUserId.isPresent()) {
            return new ResponseEntity<>("You can't create another profile", HttpStatus.NOT_ACCEPTABLE);
        }

        userProfile.setUser(user);
        userProfile.setEmail(user.getUsername());
        UserProfile savedProfile = userProfileService.saveUserProfile(userProfile);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }
    
    @PatchMapping("/update-user-profile")
    public ResponseEntity<?> UpdateProfile(@Valid @RequestBody UserProfileRequest userProfile, HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        UserProfile savedProfile = userProfileService.updateUserProfile(userId, userProfile, request);
        return new ResponseEntity<>(savedProfile, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-user-profile")
    public ResponseEntity<?> GetUserProfile(@Valid HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        UserProfile profile = userProfileService.getUser(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete-user-profile")
    public ResponseEntity<?> DeleteUserProfile(@Valid HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        userProfileService.DeleteUserProfile(userId);
        return new ResponseEntity<>("Your profile has been deleted", HttpStatus.OK);
    }
    
}
