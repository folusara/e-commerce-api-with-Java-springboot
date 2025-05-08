package com.example.myEcommerceAPI.repository;

import org.springframework.stereotype.Repository;

import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.entity.UserProfile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long >{
    Optional<UserProfile> findByUserId(User user);

    Optional<UserProfile> findByUserId(Long userId);

    Optional<UserProfile> findByUser(User user);

    Optional<UserProfile> findById(UserProfile user);

    Optional<UserProfile> findByEmailIgnoreCase(String email);


}
