package com.example.myEcommerceAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myEcommerceAPI.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
