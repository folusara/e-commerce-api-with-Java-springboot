package com.example.myEcommerceAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.DataTransferObjects.AddProductToCartDTO;
import com.example.myEcommerceAPI.DataTransferObjects.ProductUpdateRequest;
import com.example.myEcommerceAPI.entity.Cart;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.UserProfile;

@Service
public interface CartService {
    Cart SaveCart(Cart cart);
    Optional<Cart> GetCart(UserProfile userProfile);
    Product SaveProduct(Product Product, UserProfile userProfile, AddProductToCartDTO addProductToCartDTO);
    Cart CreateCart(Cart cart);
    void DeleteProduct(Long productId, UserProfile userProfile);
    Optional<Product> UpdateCart(String id, ProductUpdateRequest productUpdateRequest);
    public void clearProductsInCart(Cart cart);
}
