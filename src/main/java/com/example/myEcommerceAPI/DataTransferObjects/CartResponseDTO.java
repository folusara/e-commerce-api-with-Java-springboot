package com.example.myEcommerceAPI.DataTransferObjects;

import java.util.List;

import com.example.myEcommerceAPI.entity.CartProduct;
import com.example.myEcommerceAPI.entity.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private Double total;
    private List<CartProduct> products;
    private UserProfileCreateProductResponseDTO userProfile;
}
