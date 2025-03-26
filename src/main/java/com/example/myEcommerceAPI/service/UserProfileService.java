package com.example.myEcommerceAPI.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.DataTransferObjects.UserProfileRequest;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface UserProfileService {
    Optional<UserProfile> getUserProfile(Long id);
    UserProfile getUser(Long id);
    UserProfile getUser(User user);
    void DeleteUserProfile(Long id);
    UserProfile getUserProfile(String username);
    UserProfile saveUserProfile(UserProfile userProfile);
    UserProfile updateUserProfile(Long userId, UserProfileRequest userProfileRequest, HttpServletRequest request);
}



