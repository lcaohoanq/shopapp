package com.example.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDTO {

    @NotEmpty(message = "Category name is required")
    private String name;

}
