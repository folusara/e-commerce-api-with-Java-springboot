package com.example.myEcommerceAPI.security;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.myEcommerceAPI.entrypoint.CustomAuthenticationEntryPoint;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.security.filter.AuthenticationFilter;
import com.example.myEcommerceAPI.security.filter.ExceptionHandlerFilter;
import com.example.myEcommerceAPI.security.filter.JWTAuthorizationFilter;
import com.example.myEcommerceAPI.security.manager.CustomAuthenticationManager;
import com.example.myEcommerceAPI.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    CustomAuthenticationManager customAuthenticationManager;
    CustomUserDetailsService customUserDetailsService;
    UserServiceImpl userServiceImpl;
    UserRepository userRepository;
    UserProfileRepository userProfileRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager, userServiceImpl, userRepository, userProfileRepository);
        authenticationFilter.setFilterProcessesUrl("/api/authenticate");
        http
        .headers(headers -> headers.frameOptions(frame -> frame.disable()))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/h2/**").permitAll() 
            .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
            .requestMatchers(HttpMethod.GET, SecurityConstants.FORGOT_PASSWORD_PATH).permitAll()
            .requestMatchers(HttpMethod.POST, SecurityConstants.FORGOT_PASSWORD_PATH).permitAll() 
            .requestMatchers(HttpMethod.PUT, SecurityConstants.RESET_PASSWORD_PATH + "/**").permitAll()
            .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
            .requestMatchers("/customer/**").hasAuthority("ROLE_CUSTOMER")
            .requestMatchers(HttpMethod.GET, SecurityConstants.SWAGGER_UI).permitAll()
            .requestMatchers(HttpMethod.GET, SecurityConstants.SWAGGER_UI_HTML).permitAll()
            .requestMatchers(HttpMethod.GET, SecurityConstants.API_DOCS).permitAll()
            .requestMatchers(HttpMethod.GET, "/products").permitAll()
            .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
            .anyRequest().authenticated()
                )
        .userDetailsService(customUserDetailsService)
                .exceptionHandling(exception -> exception
                .accessDeniedHandler(customAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
        .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
        .addFilter(authenticationFilter)
        .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    } 

       private AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                System.out.println("User:" + authentication);
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    System.out.println("User Role:" + authority.getAuthority());
                }
                Map<String, String> errorResponse = Map.of(
                    "error", "Access Denied",
                    "message", accessDeniedException.getMessage()
                );
                new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
            } else {
                    System.out.println("User is not authenticated.");
                }
                Map<String, String> errorResponse = Map.of(
                    "error", "Access Denied",
                    "message", accessDeniedException.getMessage()
                );
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
        };
    }
}
