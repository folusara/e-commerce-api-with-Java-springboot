package com.example.myEcommerceAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.myEcommerceAPI.DataTransferObjects.AddProductToCartDTO;
import com.example.myEcommerceAPI.DataTransferObjects.ProductUpdateRequest;
import com.example.myEcommerceAPI.DataTransferObjects.createProductDTO;
import com.example.myEcommerceAPI.entity.Cart;
import com.example.myEcommerceAPI.entity.CartProduct;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.repository.CartRepository;
import com.example.myEcommerceAPI.repository.ProductRepository;
import com.example.myEcommerceAPI.utils.JWTUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final JWTUtil JWTUtil;
    private final CartRepository cartRepository;
    private final  ProductRepository productRepository;

    @Override
    public Cart SaveCart(Cart cart) {
       return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> GetCart(UserProfile userProfile) {
        return cartRepository.findByCreatedBy(userProfile);
    }

    @Override
    public Product SaveProduct(Product product, UserProfile userProfile, AddProductToCartDTO addProductToCartDTO) {
        Optional<Cart> userCart = GetCart(userProfile);
        CartProduct cartProduct = createCartProduct(product, addProductToCartDTO);
    
        if (userCart.isPresent()) {
            Cart cart = userCart.get();
            updateCartTotal(cart);
            cart.setCreatedBy(userProfile);
            cart.getProducts().add(cartProduct);
            cartProduct.setCart(cart);


            cartRepository.save(cart);
            return product;
        }
    
        Cart newCart = new Cart();
        updateCartTotal(newCart);
        newCart.setCreatedBy(userProfile);
        newCart.getProducts().add(cartProduct);
        cartProduct.setCart(newCart);
        cartRepository.save(newCart);
    
        return product;
    }
    
    // ✅ Helper method to create a CartProduct
    private CartProduct createCartProduct(Product product, AddProductToCartDTO addProductToCartDTO) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct_description(product.getDescription());
        cartProduct.setImageUrl(product.getImage_url());
        cartProduct.setPrice(product.getPrice());
        cartProduct.setStockQuantity(addProductToCartDTO.getStockQuantity());
        cartProduct.setProduct_name(product.getName());
        cartProduct.setSubTotal(product.getPrice() * addProductToCartDTO.getStockQuantity());
        return cartProduct;
    }
    
    private void updateCartTotal(Cart cart) {
        double totalAmount = cart.getProducts().stream()
                .mapToDouble(CartProduct::getSubTotal)
                .sum();
        cart.setTotal(totalAmount);
    }

    public void clearProductsInCart(Cart cart) {
        cart.getProducts().clear();
        cartRepository.save(cart);
    }
    

    @Override
    public void DeleteProduct(Long productId, UserProfile userProfile) {
        Optional<Cart> userCart = GetCart(userProfile);
        if (userCart.isPresent()) {
            Cart cart = userCart.get();
            cart.getProducts().removeIf(product -> product.getId().equals(productId));
            cartRepository.save(cart);
        }
    }

    @Override
    public Optional<Product> UpdateCart(String id, ProductUpdateRequest productUpdateRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'UpdateCart'");
    }

    @Override
    public Cart CreateCart(Cart cart) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CreateCart'");
    }
    
}
