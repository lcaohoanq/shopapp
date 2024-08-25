package com.example.shopapp.controllers;

import com.example.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProduct(
        @ModelAttribute @Valid ProductDTO productDTO,
        //@RequestPart("file") MultipartFile file,
        BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<FieldError> fieldErrorList = result.getFieldErrors();
                List<String> errorMessages = fieldErrorList
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files = productDTO.getFiles();
            files= files == null ? new ArrayList<MultipartFile>() : files;
            for(MultipartFile file: files){
                if (file != null) {
                    if (file.getSize() > 10 * 1024 * 1024) {
                        //throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File size must be less than 10MB");
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File size must be less than 10MB");
                    }
                    String contentType = file.getContentType(); //check if the file is image
                    if (contentType == null || !contentType.startsWith("image/")) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                    }
                    //Store file
                    String fileName = storeFile(file);
                    //save to product_images later
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok("Product inserted: " + productDTO.getName());
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        //File.separator: depends on the OS, for windows it is '\', for linux it is '/'
        Path destination = Paths.get(uploadDir.toString() + File.separator + uniqueFileName);
        //copy the file to the destination
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
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
