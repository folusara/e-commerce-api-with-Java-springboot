package com.example.myEcommerceAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.myEcommerceAPI.DataTransferObjects.ProductUpdateRequest;
import com.example.myEcommerceAPI.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     Optional<Product> findByName(String name);

     @Modifying
     @Transactional
     @Query("UPDATE Product p SET p.price = :price, p.description = :description WHERE p.name = :name")
     int updateProduct(String name, double price, String description);
     void deleteByName(String name);
}
