package com.example.myEcommerceAPI.web;

import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.DataTransferObjects.CreateTagDTO;
import com.example.myEcommerceAPI.repository.TagRepository;

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
@Tag(name = "Product API", description = "API for creating products")
@RequestMapping("/api")
public class TagController {

    private final TagRepository tagRepository;

    @PostMapping("/admin/createTag")
    public ResponseEntity<?> CreateTag(@Valid @RequestBody CreateTagDTO tagDTO) {
        if (tagRepository.findByName(tagDTO.getName()).isPresent()) {
            return new ResponseEntity<>("Product Tag with that name already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        com.example.myEcommerceAPI.entity.Tag tag = new com.example.myEcommerceAPI.entity.Tag();
        tag.setName(tagDTO.getName());
        tagRepository.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }
    
    @GetMapping("/admin/Tags")
    public  ResponseEntity<?>  GetAllTags() {
      List<com.example.myEcommerceAPI.entity.Tag> allTags = tagRepository.findAll();
       return new ResponseEntity<>(allTags, HttpStatus.OK);
    }
    
}
