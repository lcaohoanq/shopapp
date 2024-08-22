package com.example.shopapp.controllers;

import com.example.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @PostMapping
    public ResponseEntity<?> insertProduct(
        @RequestBody @Valid ProductDTO productDTO,
        BindingResult result
    ) {
        if(result.hasErrors()){
            List<FieldError> fieldErrorList = result.getFieldErrors();
            List<String> errorMessages = fieldErrorList
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Product inserted: " + productDTO.getName());
    }

    @GetMapping
    public ResponseEntity<String> getProducts(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("Products page: %d, limit: %d", page, limit));
    }

    //localhost:8080/api/v1/products/5
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(
        @RequestParam("id") String productId
    ) {
        return ResponseEntity.ok("Product by id: " + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(
        @RequestParam("id") String productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted: " + productId);
    }

}
