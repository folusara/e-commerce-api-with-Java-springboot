package com.example.myEcommerceAPI.web;

import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.DataTransferObjects.CreateTagDTO;
import com.example.myEcommerceAPI.entity.Category;
import com.example.myEcommerceAPI.repository.CategoryRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@AllArgsConstructor
@RestController
@Tag(name = "Product Category API", description = "API for managing product category")
@RequestMapping("/api")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @PostMapping("/admin/createCategory")
    public ResponseEntity<?> CreateCategory(@Valid @RequestBody CreateTagDTO tagDTO) {
        if (categoryRepository.findByName(tagDTO.getName()).isPresent()) {
            return new ResponseEntity<>("Product Category with that name already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        Category category = new com.example.myEcommerceAPI.entity.Category();
        category.setName(tagDTO.getName());
        categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
    
    @GetMapping("/admin/categories")
    public  ResponseEntity<?>  GetAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
       return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }
    
}
