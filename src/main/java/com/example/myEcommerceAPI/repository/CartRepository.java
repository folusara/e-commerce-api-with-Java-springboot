package com.example.myEcommerceAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myEcommerceAPI.entity.Cart;
import com.example.myEcommerceAPI.entity.Category;
import com.example.myEcommerceAPI.entity.UserProfile;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Long> {
     Optional<Cart> findByCreatedBy(UserProfile createdBy);
}
