package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDTO {

    //id INT PRIMARY KEY AUTO_INCREMENT,

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 200, message = "Product name must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be equal or greater than 0")
    @Max(value = 1000000, message = "Price must be equal or less than 1000000")
    private float price;

    private String thumbnail;
    private String description;
    //created at and updated at auto generated
    @JsonProperty("category_id")
    private int categoryId;

    private MultipartFile file;

}
