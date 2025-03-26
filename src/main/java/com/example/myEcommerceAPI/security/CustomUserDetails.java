package com.example.myEcommerceAPI.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.enums.Role;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final UserProfile userProfile;
    private final List<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(User user, UserProfile userProfile, List<SimpleGrantedAuthority> authorities) {
        this.user = user != null ? user : new User();
        this.userProfile = userProfile;
        this.authorities = authorities != null ? authorities : null;

    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        System.err.println("checkinggggg");
        if (userProfile != null && userProfile.getRoles() != null) {
            return userProfile.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    
@Override
public String getPassword() {
    return null;
}
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Customize if you track account expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customize if you track locked accounts
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customize if you track credential expiration
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return UserDetails.super.isEnabled();
    }

    // @Override
    // public boolean isEnabled() {
    //     return user.isVerified(); // Only allow verified users
    // }

    public UserProfile getUser() {
        return userProfile;
    }
}
