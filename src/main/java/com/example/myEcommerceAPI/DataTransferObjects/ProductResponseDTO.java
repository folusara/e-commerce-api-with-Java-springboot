package com.example.myEcommerceAPI.DataTransferObjects;

import java.math.BigDecimal;
import java.util.List;

import com.example.myEcommerceAPI.entity.Tag;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private CategoryResponseDTO category;
    private UserProfileCreateProductResponseDTO userProfile;
    private List<CreateTagDTO> tags;
    public void setTags(List<Tag> tagDTOs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTags'");
    }
}
