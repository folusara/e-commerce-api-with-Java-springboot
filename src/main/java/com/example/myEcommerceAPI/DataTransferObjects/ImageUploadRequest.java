package com.example.myEcommerceAPI.DataTransferObjects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadRequest {
    private String image;
    
    @Override
    public String toString() {
        try {
            // Convert the object to JSON string for meaningful logging
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // If JSON conversion fails, return the default toString representation
            return super.toString();
        }
    }
}
