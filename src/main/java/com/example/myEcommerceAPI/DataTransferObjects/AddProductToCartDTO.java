package com.example.myEcommerceAPI.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToCartDTO {
    private Long productId;
    private Long stockQuantity;
}
