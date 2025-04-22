package com.example.myEcommerceAPI.DataTransferObjects;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagDTO {
    @NotBlank( message = "Include name of tag")
    private String name;
}
