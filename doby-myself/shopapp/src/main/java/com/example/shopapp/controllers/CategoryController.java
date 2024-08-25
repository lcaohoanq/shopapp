package com.example.shopapp.controllers;

import com.example.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    @GetMapping("") // localhost:8080/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getAllCategories(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("Categories page: %d, limit: %d", page, limit));
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult result) {
// Spring will perform null check with the deserialization @RequestBody before reach the null check below
//        if(categoryDTO == null){
//            return ResponseEntity.badRequest().body("Please provide category data");
//        }

// user @Valid (and Binding Result) to validate the annotation from DTO

        if(result.hasErrors()){
            List<FieldError> fieldErrorList = result.getFieldErrors();
            List<String> errorMessages = fieldErrorList
                                            .stream()
                                            .map(FieldError::getDefaultMessage)
                                            .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Insert Success: "  + categoryDTO.getName());
    }

    /*
    method: PUT
    path: localhost:8080/api/v1/categories/5
    */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Category updated: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Category deleted: " + id);
    }

}
