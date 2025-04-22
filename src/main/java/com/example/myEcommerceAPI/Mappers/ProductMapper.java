package com.example.myEcommerceAPI.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.myEcommerceAPI.DataTransferObjects.CategoryResponseDTO;
import com.example.myEcommerceAPI.DataTransferObjects.CreateTagDTO;
import com.example.myEcommerceAPI.DataTransferObjects.ProductResponseDTO;
import com.example.myEcommerceAPI.DataTransferObjects.UserProfileCreateProductResponseDTO;
import com.example.myEcommerceAPI.entity.Category;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.Tag;
import com.example.myEcommerceAPI.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toDto(Product product);
    UserProfileCreateProductResponseDTO toDto(UserProfile userProfile);
    CategoryResponseDTO toDto(Category category);
    CreateTagDTO toDto(Tag tag);
}
