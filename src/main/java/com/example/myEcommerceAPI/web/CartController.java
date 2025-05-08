package com.example.myEcommerceAPI.web;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.DataTransferObjects.AddProductToCartDTO;
import com.example.myEcommerceAPI.DataTransferObjects.CartCheckoutDTO;
import com.example.myEcommerceAPI.DataTransferObjects.CartResponseDTO;
import com.example.myEcommerceAPI.DataTransferObjects.DeleteProductFromCartDTO;
import com.example.myEcommerceAPI.DataTransferObjects.UserProfileCreateProductResponseDTO;
import com.example.myEcommerceAPI.entity.Cart;
import com.example.myEcommerceAPI.entity.CartProduct;
import com.example.myEcommerceAPI.entity.Order;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.Transaction;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.enums.OrderStatus;
import com.example.myEcommerceAPI.enums.PaymentStatus;
import com.example.myEcommerceAPI.repository.CartRepository;
import com.example.myEcommerceAPI.repository.OrderRepository;
import com.example.myEcommerceAPI.repository.ProductRepository;
import com.example.myEcommerceAPI.repository.TransactionRepository;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.service.CartServiceImpl;
import com.example.myEcommerceAPI.utils.JWTUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@RestController
@Tag(name = "Product Category API", description = "API for managing Users' cart")
@RequestMapping("/api")
public class CartController {


    private final CartRepository cartRepository;
    private final CartServiceImpl cartServiceImpl;
    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;
    private final JWTUtil JWTUtil;

    @PostMapping("/add-product-to-cart")
    public ResponseEntity<?> AddProductToCart(@Valid @RequestBody AddProductToCartDTO addProductToCartDTO,
            HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserId(userId);
        Optional<Product> productOptional = productRepository.findById(addProductToCartDTO.getProductId());
        if (userProfileOptional.isEmpty() || productOptional.isEmpty()) {
            String missing = userProfileOptional.isEmpty() ? "User profile" : "Product";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(Map.of("message", missing + " does not exist"));
        }
        Optional<Cart> cartOptional = cartServiceImpl.GetCart(userProfileOptional.get());
        if (cartOptional.isPresent()) {
            boolean productAlreadyInCart = cartOptional.get().getProducts().stream()
                    .anyMatch(cartProduct -> cartProduct.getProduct_name().equals(productOptional.get().getName()));
            if (productAlreadyInCart) {
                return ResponseEntity.status(401).body(Map.of("message", "Product is already in cart"));
            }
        }
        cartServiceImpl.SaveProduct(productOptional.get(), userProfileOptional.get(), addProductToCartDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("message", "Product has been added to your cart"));
    }
    
    @DeleteMapping("/delete-product-from-cart")
    public ResponseEntity<?> DeleteProductFromCart(@Valid @RequestBody DeleteProductFromCartDTO deleteProductFromCartDTO, HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserId(userId);
        Optional<Product> productOptional = productRepository.findById(deleteProductFromCartDTO.getProductId());
        if (userProfileOptional.isEmpty() || productOptional.isEmpty()) {
            String missing = userProfileOptional.isEmpty() ? "User profile" : "Product";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(Map.of("message", missing + " does not exist"));
        }
        Optional<Cart> cartOptional = cartServiceImpl.GetCart(userProfileOptional.get());
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            boolean productInCart = cartOptional.get().getProducts().removeIf(cartProduct -> cartProduct.getProduct_name().equals(productOptional.get().getName()));
            if (productInCart) {
                cartRepository.save(cart);
                return ResponseEntity.status(200).body(Map.of("message", "Product has been removed from your cart"));
            } else {
                return ResponseEntity.status(401).body(Map.of("message", "Product not in your cart")); 
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("message", "Cart not found"));
    }


    @PostMapping("/cart/checkout")
    public ResponseEntity<?> cartCheckout(@Valid @RequestBody CartCheckoutDTO cartCheckoutDTO, HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserId(userId);
        Optional<Cart> cart = cartRepository.findById(cartCheckoutDTO.getCartId());
        if (cart.isEmpty() || cart.get().getProducts().size() == 0) {
            String message = cart.isEmpty() ? "Cart does not exist" : "No products in cart";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(Map.of("message", message));
        }
        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(Map.of("message", "Profile does not exist"));
        }
        UserProfileCreateProductResponseDTO userProfileCreateProductResponseDTO = new UserProfileCreateProductResponseDTO();
        userProfileCreateProductResponseDTO.setEmail(userProfileOptional.get().getEmail());
        userProfileCreateProductResponseDTO.setFirstName(userProfileOptional.get().getFirstName());
        userProfileCreateProductResponseDTO.setLastName(userProfileOptional.get().getLastName());
        userProfileCreateProductResponseDTO.setPhoneNumber(userProfileOptional.get().getPhoneNumber());
        userProfileCreateProductResponseDTO.setId(userProfileOptional.get().getId());
      
        Transaction transaction = new Transaction(null,"TRX_shhw23hh3", cart.get().getTotal(), "Flutterwave",
                cartCheckoutDTO.getPaymentStatus(), userProfileOptional.get(), LocalDateTime.now(ZoneOffset.UTC));
                transactionRepository.save(transaction);
        if (cartCheckoutDTO.getPaymentStatus() == PaymentStatus.FAILED) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "Order failed"));
        }
        Order order = new Order(null, userProfileOptional.get().getBillingAddress(), cart.get().getTotal(),
                OrderStatus.PENDING, transaction, userProfileOptional.get(), LocalDateTime.now(ZoneOffset.UTC));

                System.err.println("total"+cart.get().getTotal());
        orderRepository.save(order);
        cartServiceImpl.clearProductsInCart(cart.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("order", order, "message", "Order successfully created"));
    }
    
    @GetMapping("/view-cart")
    public ResponseEntity<?> viewCart(HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        Optional<UserProfile> userOptional = userProfileRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(Map.of("message", "User profile does not exist"));
        }

        Optional<Cart> cartOptional = cartServiceImpl.GetCart(userOptional.get());

        if (cartOptional.isEmpty() || cartOptional.get().getProducts().isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "Your cart is empty"));
        }

        CartResponseDTO cartDto = toDto(cartOptional.get(), userOptional.get());
        return ResponseEntity.ok(Map.of(
                "message", "Cart retrieved successfully",
                "cart", cartDto));
    }

    public CartResponseDTO toDto(Cart cart, UserProfile userProfile) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        double totalAmount = cart.getProducts().stream()
                .mapToDouble(CartProduct::getSubTotal)
                .sum();
        dto.setTotal(totalAmount);
        dto.setProducts(cart.getProducts());
        List<CartProduct> cartProducts = cart.getProducts().stream()
            .map(product -> {
                CartProduct cartProduct = new CartProduct();
                cartProduct.setId(product.getId());
                cartProduct.setPrice(product.getPrice());
                cartProduct.setStockQuantity(product.getStockQuantity());
                cartProduct.setSubTotal(product.getSubTotal());
                cartProduct.setProduct_name(product.getProduct_name());
                cartProduct.setProduct_description(product.getProduct_description());
                cartProduct.setImageUrl(product.getImageUrl());
                return cartProduct;
            })
                .collect(Collectors.toList());
        dto.setProducts(cartProducts);
        UserProfileCreateProductResponseDTO userProfileDTO = new UserProfileCreateProductResponseDTO();
        userProfileDTO.setId(userProfile.getId());
        userProfileDTO.setFirstName(userProfile.getFirstName());
        userProfileDTO.setLastName(userProfile.getLastName());
        userProfileDTO.setEmail(userProfile.getEmail());
        userProfileDTO.setPhoneNumber(userProfile.getPhoneNumber());
    
        dto.setUserProfile(userProfileDTO);
        return dto;
    }
    
}
