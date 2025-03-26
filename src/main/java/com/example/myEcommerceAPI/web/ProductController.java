package com.example.myEcommerceAPI.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.repository.ProductRepository;
import com.example.myEcommerceAPI.service.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
// @RequestMapping("/admin")
public class ProductController {

    ProductService productService;
    ProductRepository productRepository;

    @PostMapping("/admin/create-product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            return new ResponseEntity<>("Product with that name already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(productService.SaveProduct(product), HttpStatus.CREATED);
    }
    
    @PostMapping("/admin/update-product")
    public ResponseEntity<?> UpdateProduct(@Valid @RequestBody Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            return new ResponseEntity<>("Product with that name already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(productService.SaveProduct(product), HttpStatus.CREATED);
    }
    
    @PostMapping("/products")
    public ResponseEntity<?> GetProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.GetProducts(), HttpStatus.CREATED);
    }
    
}
