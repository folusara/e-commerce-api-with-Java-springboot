package com.example.myEcommerceAPI.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.exception.EntityNotFoundException;
import com.example.myEcommerceAPI.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user,  404L);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User ResetPassword(Long id, String password) {
        Optional <User> searchedUser = userRepository.findById(id);
        if (searchedUser.isPresent()) {
            User user = searchedUser.get();
            user.setPassword(bCryptPasswordEncoder.encode(password));
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException(id, User.class);
        }
       
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }


}
