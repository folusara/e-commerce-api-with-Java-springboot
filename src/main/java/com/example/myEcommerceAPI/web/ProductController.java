package com.example.myEcommerceAPI.web;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.DataTransferObjects.CategoryResponseDTO;
import com.example.myEcommerceAPI.DataTransferObjects.CreateTagDTO;
import com.example.myEcommerceAPI.DataTransferObjects.ProductResponseDTO;
import com.example.myEcommerceAPI.DataTransferObjects.UserProfileCreateProductResponseDTO;
import com.example.myEcommerceAPI.DataTransferObjects.createProductDTO;
import com.example.myEcommerceAPI.Mappers.ProductMapper;
import com.example.myEcommerceAPI.entity.Category;
import com.example.myEcommerceAPI.entity.Product;
import com.example.myEcommerceAPI.entity.UserProfile;
import com.example.myEcommerceAPI.repository.CategoryRepository;
import com.example.myEcommerceAPI.repository.ProductRepository;
import com.example.myEcommerceAPI.repository.TagRepository;
import com.example.myEcommerceAPI.repository.UserProfileRepository;
import com.example.myEcommerceAPI.repository.UserRepository;
import com.example.myEcommerceAPI.service.ProductService;
import com.example.myEcommerceAPI.utils.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "Product API", description = "API for managing products")
@RequestMapping("/api")
public class ProductController {

   private final JWTUtil JWTUtil;

   private final ProductService productService;
   private final ProductRepository productRepository;
   private final UserRepository userRepository;
   private final UserProfileRepository userProfileRepository;
   private final CategoryRepository categoryRepository;
   private final TagRepository tagRepository;
   private final ProductMapper productMapper;

    @Operation(summary = "Create product by admin")
    @PostMapping("/admin/create-product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody createProductDTO product, HttpServletRequest request) {
        Long userId = JWTUtil.extractUserId(request);
        Product newProduct = new Product();
        if (productRepository.findByName(product.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", "Product with that name already exists"));
        }
        Optional<UserProfile> userprofileOptional = userProfileRepository.findByUserId(userId);
        Optional<Category> categoryOptional = categoryRepository.findById(product.getCategory_id());
        if (!categoryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(Map.of("message", "Product category with that name does not exists"));
        }
        List<com.example.myEcommerceAPI.entity.Tag> tags = tagRepository.findAllById(product.getTags());
        if (tags.size() != product.getTags().size()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(Map.of("message", "One or more tag IDs are invalid. Please provide valid tag IDs."));
        }
        newProduct.setUserProfile(userprofileOptional.get());
        newProduct.setTags(tags);
        newProduct.setCategory(categoryOptional.get());
        newProduct.setImage_url(product.getImage_url());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        Product savedProduct = productService.SaveProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toDto(savedProduct));
    }
    
    @Operation(summary = "Update product by name")
    @PutMapping("/admin/update-product/{name}")
    public ResponseEntity<?> updateProduct(@PathVariable String name, @Valid @RequestBody Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findByName(name);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
            product.setImage_url(updatedProduct.getImage_url());
            product.setTags(updatedProduct.getTags());
            return new ResponseEntity<>(productService.SaveProduct(product), HttpStatus.OK);
        }
        return new ResponseEntity<>("Product with that name does not exist", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "get single product")
    @GetMapping("/product/{name}")
    public ResponseEntity<?> GetProduct(@PathVariable String name) {
        Optional<Product> searchedProduct = productRepository.findByName(name);
        if (searchedProduct.isPresent()) {
            return new ResponseEntity<>(searchedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
    }
    
    @Operation(summary = "get products")
    @GetMapping("/products")
    public ResponseEntity<?> GetProducts() {
        List<Product> products = productService.GetProducts();
        if (!products.isEmpty()) {
            List<ProductResponseDTO> dtoList = products.stream().map(this::toDto).collect(Collectors.toList());
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>("No products currently", HttpStatus.OK);
    }

   public ProductResponseDTO toDto(Product product) {
    ProductResponseDTO dto = new ProductResponseDTO();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setPrice(product.getPrice());
    dto.setImageUrl(product.getImage_url());

    CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
    categoryDTO.setName(product.getCategory().getName());
    dto.setCategory(categoryDTO);

    UserProfileCreateProductResponseDTO userDTO = new UserProfileCreateProductResponseDTO();
    userDTO.setId(product.getUserProfile().getId());
    userDTO.setFirstName(product.getUserProfile().getFirstName());
    userDTO.setLastName(product.getUserProfile().getLastName());
    userDTO.setEmail(product.getUserProfile().getEmail());
    userDTO.setPhoneNumber(product.getUserProfile().getPhoneNumber());
    dto.setUserProfile(userDTO);

    List<CreateTagDTO> tagDTOs = product.getTags().stream().map(tag -> {
        CreateTagDTO tagDTO = new CreateTagDTO();
        tagDTO.setName(tag.getName());
        return tagDTO;
    }).collect(Collectors.toList());
    dto.setTags(tagDTOs);
    return dto;
}

}
