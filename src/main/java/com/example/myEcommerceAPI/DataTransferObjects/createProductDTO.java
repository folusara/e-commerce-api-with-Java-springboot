package com.example.myEcommerceAPI.DataTransferObjects;

import java.util.List;

import com.example.myEcommerceAPI.entity.Inventory;
import com.example.myEcommerceAPI.entity.UserProfile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class createProductDTO {
    @NotNull(message = "Price is required")
    private double price;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotBlank(message = "Product image is required")
    private String image_url;
 
    // @NotNull(message = "User profile id is required")
    // private Number userProfileId;

    @NotNull(message = "Product category Id is required")
    private Long category_id;

    @NotNull(message = "Kindly add at least one product Tag")
    private List<Long> tags;
    
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Inventory inventory;
}
