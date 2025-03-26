package com.example.myEcommerceAPI.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.exception.EntityNotFoundException;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
     UserRepository userRepository;
     UserProfileRepository userProfileRepository;
     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserProfile userProfile = userProfileRepository.findByUser(user)
                 .orElseThrow(() -> new EntityNotFoundException(user.getId(), UserProfile.class));
        List<SimpleGrantedAuthority> authorities = userProfile.getRoles().stream()
                 .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                 .collect(Collectors.toList());
        return new CustomUserDetails(user, userProfile, authorities);
     }
}


