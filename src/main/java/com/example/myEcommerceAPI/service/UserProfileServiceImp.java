package com.example.myEcommerceAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.DataTransferObjects.UserProfileRequest;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.enums.Role;
import com.example.myEcommerceAPI.exception.EntityNotFoundException;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.utils.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserProfileServiceImp implements UserProfileService {
    @Autowired
    UserProfileRepository userProfileRepository;
    UserRepository userRepository;

    @Override
    public void DeleteUserProfile(Long id) {
       
        Optional<UserProfile> existingProfile = userProfileRepository.findByUserId(id);
        if (existingProfile.isPresent()) {
            userProfileRepository.delete(existingProfile.get());
            return;
        } else {
            throw new EntityNotFoundException(id, UserProfile.class);
        }
    }

    @Override
    public UserProfile getUserProfile(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<UserProfile> getUserProfile(Long id) {
        return userProfileRepository.findById(id);
    }
    
    @Override
    public UserProfile getUser(Long id) {
        UserProfile userProfile = userProfileRepository.findByUserId(id).orElse(null);
        if (userProfile != null) {
            return userProfile;
        } else {
            throw new EntityNotFoundException(id, UserProfile.class);
        }
    }
    
    @Override
    public UserProfile getUser(User user) {
        // return userProfileRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException(user.getId(), UserProfile.class));
        return userProfileRepository.findByUser(user).orElse(null);
    }

    @Override
    public UserProfile saveUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    

    @Override
    public UserProfile updateUserProfile(Long userId, UserProfileRequest userProfileRequest, HttpServletRequest request) {
        System.err.println(userId);
        
        UserProfile existingProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        if (userProfileRequest.getFirstName() != null) {
            existingProfile.setFirstName(userProfileRequest.getFirstName());
        }
        if (userProfileRequest.getLastName() != null) {
            existingProfile.setLastName(userProfileRequest.getLastName());
        }
        if (userProfileRequest.getPhoneNumber() != null) {
            existingProfile.setPhoneNumber(userProfileRequest.getPhoneNumber());
        }
        if (userProfileRequest.getCity() != null) {
            existingProfile.setCity(userProfileRequest.getCity());
        }
        if (userProfileRequest.getCountry() != null) {
            existingProfile.setCountry(userProfileRequest.getCountry());
        }
        if (userProfileRequest.getRoles() != null) {
            existingProfile.setRoles((List<Role>) userProfileRequest.getRoles());
        }
        if (userProfileRequest.getDateOfBirth() != null) {
            existingProfile.setDateOfBirth(userProfileRequest.getDateOfBirth());
        }
        if (userProfileRequest.getGender() != null) {
            existingProfile.setGender(userProfileRequest.getGender());
        }
    
        // Save the updated profile
        return userProfileRepository.save(existingProfile);
    }
    
    

}
