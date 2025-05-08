package com.example.myEcommerceAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myEcommerceAPI.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
