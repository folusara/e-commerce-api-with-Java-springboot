package com.example.myEcommerceAPI.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myEcommerceAPI.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
