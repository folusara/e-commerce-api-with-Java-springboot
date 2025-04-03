package com.example.myEcommerceAPI.web;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.repository.ProductRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.service.ProductService;
import com.example.myEcommerceAPI.utils.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "Product API", description = "API for managing products")
public class ProductController {

   private final JWTUtil JWTUtil;

   private final ProductService productService;
   private final ProductRepository productRepository;
   private final UserRepository userRepository;

    // ProductController(JWTUtil JWTUtil) {
    //     this.JWTUtil = JWTUtil;
    // }

    @Operation(summary = "Create product by admin")
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
    
    @Operation(summary = "update product")
    @PostMapping("/admin/update-product")
    public ResponseEntity<?> UpdateProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.SaveProduct(product), HttpStatus.CREATED);
    }

    @Operation(summary = "get single product")
    @GetMapping("/product/{name}")
    public ResponseEntity<?> GetProduct(@PathVariable String name) {
        // if (productRepository.findByName(product.getName()).isPresent()) {
        //     return new ResponseEntity<>("Product with that name already exists", HttpStatus.NOT_ACCEPTABLE);
        // }
        Optional<Product> searchedProduct = productRepository.findByName(name);
        System.err.println("serached" + searchedProduct);
        System.err.println("serached" +name);
        if (searchedProduct.isPresent()) {
            return new ResponseEntity<>(searchedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
    }
    
    @Operation(summary = "get products")
    @GetMapping("/products")
    public ResponseEntity<?> GetProducts() {
        List <Product> products = productService.GetProducts();
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        return  new ResponseEntity<>("No products currently", HttpStatus.OK);
    }
}
