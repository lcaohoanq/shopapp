package com.example.shopapp.controllers;

import com.example.shopapp.constants.ErrorMessage;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.services.IProductService;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping(value = "")
    public ResponseEntity<?> createProduct(
        @Valid @RequestBody ProductDTO productDTO,
        //@ModelAttribute("files") List<MultipartFile> files,
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
            //need to save the product first to get the product id, get the id and add the image
            Product newProduct = productService.createProduct(productDTO);
//            productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
        @PathVariable("id") Long productId,
        @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body(
                    ErrorMessage.MAXIMUM_IMAGES_PER_PRODUCT
                + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT
                + " found: " + files.size());
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }

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
                ProductImage productImage = productService.createProductImage(
                    existingProduct.getId(),
                    ProductImageDTO.builder()
                        .imageUrl(fileName)
                        .build());
                //save to product_images later
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    private String storeFile(MultipartFile file) throws IOException {
        if(!isImageFile(file) || file.getOriginalFilename() == null){
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
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
