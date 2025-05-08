package com.example.myEcommerceAPI.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class cartProductDTO {
    private Long id;
    private double price;
    private Long stockQuantity;
    private String name;
    private String description;
    private String imageUrl;
}
