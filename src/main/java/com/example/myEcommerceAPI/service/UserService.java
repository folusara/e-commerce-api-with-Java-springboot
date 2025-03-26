package com.example.myEcommerceAPI.service;

import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.entity.User;

@Service
public interface UserService {
    User getUser(Long id);
    User getUser(String username);
    User ResetPassword(Long id, String password);
    User saveUser(User user);
}
