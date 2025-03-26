package com.example.myEcommerceAPI.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.exception.EntityNotFoundException;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.security.CustomUserDetails;

public class UserDetailsUtil {

    public static CustomUserDetails loadUser(UserRepository userRepository, UserProfileRepository userProfileRepository,
            String username) throws UsernameNotFoundException {
        System.err.println("teesting");
                System.err.println(username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.err.println(user);
        System.err.println("hello, auth trying");
        UserProfile userProfile = userProfileRepository.findByUser(user).orElse(null);
        System.err.println("hello, auth trying");
        // Handle missing roles by providing a default role
        List<SimpleGrantedAuthority> authorities = (userProfile != null && userProfile.getRoles() != null)
                ? userProfile.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomUserDetails(user, userProfile, authorities);
    }
}
