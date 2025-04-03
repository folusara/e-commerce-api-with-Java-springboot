package com.example.myEcommerceAPI.web;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.repository.ProductRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.service.ProductService;
import com.example.myEcommerceAPI.utils.JWTUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ProductController {

    ProductService productService;
    ProductRepository productRepository;
    UserRepository userRepository;

    @PostMapping("/admin/create-product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        if (productRepository.findByName(product.getName()).isPresent()) {
            return new ResponseEntity<>("Product with that name already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            product.setUser(user.get());
            return new ResponseEntity<>(productService.SaveProduct(product), HttpStatus.CREATED);
        }
        return new ResponseEntity<>( new EntityNotFoundException("User not found"), HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/admin/update-product")
    public ResponseEntity<?> UpdateProduct(@Valid @RequestBody Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            return new ResponseEntity<>("Product with that name already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(productService.SaveProduct(product), HttpStatus.CREATED);
    }
    
    @GetMapping("/products")
    public ResponseEntity<?> GetProducts() {
        return new ResponseEntity<>(productService.GetProducts(), HttpStatus.OK);
    }
}
