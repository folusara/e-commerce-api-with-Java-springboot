package com.example.myEcommerceAPI.security.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.enums.Role;
import com.example.myEcommerceAPI.exception.EntityNotFoundException;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.security.*;
import com.example.myEcommerceAPI.service.UserServiceImpl;
import com.example.myEcommerceAPI.utils.UserDetailsUtil;


@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserServiceImpl userServiceImpl;
    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException {
        // chain.doFilter(request, response);
    }
    
   

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            CustomUserDetails userDetails = (CustomUserDetails) UserDetailsUtil.loadUser(userRepository,
                    userProfileRepository, user.getUsername());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),
                    userDetails != null ? userDetails.getAuthorities() : null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authenticated User: " + authentication.getAuthorities());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException("Error reading user credentials", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        System.err.println("aha, auth didn't work");
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {
        System.err.println("woohoo, auth works");
        System.err.println(authResult);
        // Retrieve user object from authentication
        User user = userServiceImpl.getUser(authResult.getName());
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findByEmailIgnoreCase(user.getUsername());
        // List<String> roles = new ArrayList<>();
        List<GrantedAuthority> authorities = new ArrayList<>();
            System.err.println("user:" + user.getUsername());
       if(!user.getUsername().isEmpty()) {
         Authentication newAuth = new UsernamePasswordAuthenticationToken(authResult.getPrincipal(), authResult.getCredentials(), authorities);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
       } 
       else
        if (optionalUserProfile.isPresent()) {
            UserProfile userProfile = optionalUserProfile.get();
                 authorities = userProfile.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
            userProfile.setLastLogin(LocalDateTime.now());
            userProfileRepository.save(userProfile);
            System.out.println("Roles after authentication: " + authorities);

            // Create new authentication token with updated authorities
            Authentication newAuth = new UsernamePasswordAuthenticationToken(authResult.getPrincipal(), authResult.getCredentials(), authorities);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            
            // UserDetails userDetails = new CustomUserDetails(null,userProfile, null);
        } else {
            throw new UsernameNotFoundException("User not found: " + user.getUsername());
        }
        // Generate JWT Token
        List<String> roleNames = authorities.stream()
            .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        System.err.println("Final roles assigned: " + roleNames);
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withClaim("user_id", user.getId())
                .withClaim("roles", roleNames)
                // .withClaim("roles", roles.stream().map(Role::role).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));

        // Set token in response header
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
    }

    public AuthenticationFilter() {
    }

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationFilter authenticationManager(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
        return this;
    }


    @Override
    public String toString() {
        return "{" +
            " authenticationManager='" + getAuthenticationManager() + "'" +
            "}";
    }
    
    
    
}
