package com.example.myEcommerceAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.DataTransferObjects.ProductUpdateRequest;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.repository.ProductRepository;
import com.example.myEcommerceAPI.utils.JWTUtil;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    private final JWTUtil JWTUtil;
    private final ProductRepository productRepository;

    ProductServiceImpl(JWTUtil JWTUtil, ProductRepository productRepository) {
        this.JWTUtil = JWTUtil;
        this.productRepository = productRepository;
    }

    @Override
    public Product SaveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> GetProduct(String id) {
        return productRepository.findByName(id);
    }

    @Override
    public void DeleteProduct(String name) {
        Optional<Product> foundProduct = productRepository.findByName(name);
        if (foundProduct.isEmpty()) {
            throw new EntityNotFoundException("Product not found: " + name);
        }
        productRepository.deleteByName(name);
    }

    public List<Product> GetProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> UpdateProduct(String name, ProductUpdateRequest productUpdateRequest) {
        Optional<Product> foundProductOptional = productRepository.findByName(name);
        if (foundProductOptional.isEmpty()) {
            throw new EntityNotFoundException("Product not found: " + name);
        } 
        Product product = foundProductOptional.get();
        if (productUpdateRequest.getPrice() != null) {
            product.setPrice(productUpdateRequest.getPrice());
        }
        if (productUpdateRequest.getDescription() != null) {
            product.setDescription(productUpdateRequest.getDescription());
        }
        Product updatedProduct = productRepository.save(product);
        return Optional.of(updatedProduct);
    }

}
