package com.example.myEcommerceAPI.DataTransferObjects;

import java.math.BigDecimal;
import java.util.List;

import com.example.myEcommerceAPI.entity.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private CategoryResponseDTO category;
    private UserProfileCreateProductResponseDTO userProfile;
    private List<CreateTagDTO> tags;
}
