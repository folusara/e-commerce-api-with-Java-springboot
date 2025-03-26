package com.example.myEcommerceAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.DataTransferObjects.ProductUpdateRequest;
import com.example.myEcommerceAPI.entity.Product;

@Service
public interface ProductService {
    Product SaveProduct(Product product);
    Optional<Product> GetProduct(String id);
    void DeleteProduct(String id);
    Optional<Product> UpdateProduct(String id, ProductUpdateRequest productUpdateRequest);
    List<Product> GetProducts();
}



